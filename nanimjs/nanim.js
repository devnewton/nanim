function nanim_play(nanimUrl, canvasId, nanimationName) {

	if(!window.requestAnimFrame) {
		window.requestAnimFrame = (function(){   
		   return window.requestAnimationFrame  ||    
		     window.webkitRequestAnimationFrame ||    
		     window.mozRequestAnimationFrame ||    
		     window.oRequestAnimationFrame  ||    
		     window.msRequestAnimationFrame  ||    
		     function(callback, element){   
		      window.setTimeout(callback, 1000 / 60);   
		     };   
		   })(); 
	}

	var xhr = new XMLHttpRequest();
	xhr.open('GET', nanimUrl, true);
	xhr.responseType = 'arraybuffer';
	xhr.onload = function(e) {
	  if (this.status == 200) {
	    var stream = new PROTO.ArrayBufferStream(this.response, this.response.byteLength);
	    var nanimMsg = new im.bci.nanim.Nanim;
	    var nanim = nanimMsg.ParseFromStream(stream);
	    nanim_start(nanimMsg, canvasId, nanimationName);
	  }
	};
	xhr.send();
}

function nanim_find_animation_index(nanim, name) {
	for (var i=0; i<nanim.animations.length; i++) {
		var animation = nanim.animations[i];
		if(animation.name == name) {
			return i;
		}
	}
	return -1;
}

function nanim_start(nanimMsg, canvasId, nanimationName) {
	var ctx = document.getElementById(canvasId).getContext("2d");
	var nanim = {};
	nanim.images = nanim_load_images(ctx, nanimMsg);
	nanim.animations = nanimMsg.animations;
	nanim.currentAnimationIndex = nanim_find_animation_index(nanim, nanimationName);
	nanim.currentFrameIndex  = 0;
	nanim.currentFrameStartTime = new Date().getTime();
	nanim_draw(ctx,nanim);
		
	var nanimate = function() {
		var animation = nanim.animations[nanim.currentAnimationIndex];
		var frame = animation.frames[nanim.currentFrameIndex];
		var currentTime = new Date().getTime();
		var elapsed = currentTime - nanim.currentFrameStartTime;
		if(elapsed > frame.durationInMs) {
			nanim.currentFrameIndex = (nanim.currentFrameIndex + 1) % animation.frames.length;
			nanim.currentFrameStartTime = currentTime;
		}
		nanim_draw(ctx, nanim);
		window.requestAnimFrame(this);
	}
		
	window.requestAnimFrame(nanimate);
}

function nanim_load_images(ctx, nanimMsg) {
	var images ={};
	for(var n = 0; n< nanimMsg.images.length; ++n ) {
		var nimage = nanimMsg.images[n];
		var image = ctx.createImageData(nimage.width, nimage.height);
		for (var i=0; i<nimage.pixels.length; i++) {
			image.data[i] = nimage.pixels[i];
		}
		images[nimage.name] = image;
	}
	return images;
}

function nanim_draw(ctx, nanim) {
	var animation = nanim.animations[nanim.currentAnimationIndex];
	var frame = animation.frames[nanim.currentFrameIndex];
	var image = nanim.images[frame.imageName];
	ctx.putImageData(image, 0, 0, frame.u1 * image.width, frame.v1 * image.width, frame.u2 * image.width, frame.v2 * image.height);
}

"use strict";
/** @suppress {duplicate}*/var im;
if (typeof(im)=="undefined") {im = {};}
if (typeof(im.bci)=="undefined") {im.bci = {};}
if (typeof(im.bci.nanim)=="undefined") {im.bci.nanim = {};}

im.bci.nanim.Frame = PROTO.Message("im.bci.nanim.Frame",{
	imageName: {
		options: {},
		multiplicity: PROTO.required,
		type: function(){return PROTO.string;},
		id: 1
	},
	durationInMs: {
		options: {},
		multiplicity: PROTO.required,
		type: function(){return PROTO.int32;},
		id: 2
	},
	u1: {
		options: {},
		multiplicity: PROTO.required,
		type: function(){return PROTO.Float;},
		id: 3
	},
	v1: {
		options: {},
		multiplicity: PROTO.required,
		type: function(){return PROTO.Float;},
		id: 4
	},
	u2: {
		options: {},
		multiplicity: PROTO.required,
		type: function(){return PROTO.Float;},
		id: 5
	},
	v2: {
		options: {},
		multiplicity: PROTO.required,
		type: function(){return PROTO.Float;},
		id: 6
	}});
im.bci.nanim.Animation = PROTO.Message("im.bci.nanim.Animation",{
	name: {
		options: {},
		multiplicity: PROTO.required,
		type: function(){return PROTO.string;},
		id: 1
	},
	frames: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return im.bci.nanim.Frame;},
		id: 2
	}});
im.bci.nanim.PixelFormat= PROTO.Enum("im.bci.nanim.PixelFormat",{
		RGB_888 :1,
		RGBA_8888 :2});
im.bci.nanim.Image = PROTO.Message("im.bci.nanim.Image",{
	name: {
		options: {},
		multiplicity: PROTO.required,
		type: function(){return PROTO.string;},
		id: 1
	},
	width: {
		options: {},
		multiplicity: PROTO.required,
		type: function(){return PROTO.int32;},
		id: 2
	},
	height: {
		options: {},
		multiplicity: PROTO.required,
		type: function(){return PROTO.int32;},
		id: 3
	},
	format: {
		options: {},
		multiplicity: PROTO.required,
		type: function(){return im.bci.nanim.PixelFormat;},
		id: 4
	},
	pixels: {
		options: {},
		multiplicity: PROTO.required,
		type: function(){return PROTO.bytes;},
		id: 5
	}});
im.bci.nanim.Nanim = PROTO.Message("im.bci.nanim.Nanim",{
	images: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return im.bci.nanim.Image;},
		id: 1
	},
	animations: {
		options: {},
		multiplicity: PROTO.repeated,
		type: function(){return im.bci.nanim.Animation;},
		id: 2
	},
	author: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 3
	},
	license: {
		options: {},
		multiplicity: PROTO.optional,
		type: function(){return PROTO.string;},
		id: 4
	}});

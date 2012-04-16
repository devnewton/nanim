package im.bci;

import im.bci.nanim.NanimParser;
import im.bci.nanim.NanimParser.Animation;
import im.bci.nanim.NanimParser.Frame;
import im.bci.nanim.NanimParser.Image;
import im.bci.nanim.NanimParser.Nanim;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.google.protobuf.ByteString;

/**
 * Hello world!
 * 
 */
public class NanimView extends JFrame {
	private static final long serialVersionUID = -5634646781822978236L;
	private CommandLine commandLine;
	private Nanim nanim;
	private HashMap<String, BufferedImage> loadedBufferedImages = new HashMap<String, BufferedImage>();

	class NanimViewPanel extends JPanel implements ActionListener {

		private static final long serialVersionUID = 1248894247319903536L;
		int currentAnimation = 0;
		int currentFrame = 0;

		public NanimViewPanel() {
			setDoubleBuffered(true);
			updateAnimation();
			setPreferredSize(computeMaxFrameSize());
		}

		private void updateAnimation() {
			++currentFrame;
			Animation animation = nanim.getAnimations(currentAnimation);
			if (currentFrame >= animation.getFramesCount()) {
				currentFrame = 0;
				++currentAnimation;
				if (currentAnimation >= nanim.getAnimationsCount()) {
					currentAnimation = 0;
					animation = nanim.getAnimations(currentAnimation);
				}
			}
			Frame frame = animation.getFrames(currentFrame);
			Timer timer = new Timer(frame.getDuration(), this);
			timer.setRepeats(false);
			timer.start();
		}

		@Override
		public void paint(Graphics g) {
			Frame frame = nanim.getAnimations(currentAnimation).getFrames(
					currentFrame);
			// Image image = nanim.getImages()
			BufferedImage loadedImage = loadedBufferedImages.get(frame
					.getImageName());
			Image image = findImageByName(frame.getImageName());
			Graphics2D g2d = (Graphics2D) g;
			g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
			int w = image.getWidth();
			int h = image.getHeight();
			int sx1 = (int) (w * frame.getU1());
			int sy1 = (int) (h * frame.getV1());
			int sx2 = (int) (w * frame.getU2());
			int sy2 = (int) (h * frame.getV2());
			g2d.drawImage(loadedImage, 0, 0, w,
					h,
					sx1,
					sy1,
					sx2,
					sy2,
					(ImageObserver) null);
		}

		public void actionPerformed(ActionEvent e) {
			updateAnimation();
			repaint();
		}

	}

	public NanimView(CommandLine line) throws IOException {
		this.commandLine = line;
		decode();
		load();
		
		setMinimumSize(new Dimension(128, 128));

		GridBagLayout layout = new GridBagLayout();
		this.getContentPane().setLayout(layout);
		this.getContentPane().add(new NanimViewPanel());
		pack();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("nanimview");
		setVisible(true);
	}

	private Dimension computeMaxFrameSize() {
		int w = 16, h = 16;
		for(Animation animation : nanim.getAnimationsList()){
			for(Frame frame : animation.getFramesList()) {
				Image image = findImageByName(frame.getImageName());
				int frameW = Math.abs((int)(image.getWidth() * frame.getU2() - frame.getU1()));
				int frameH = Math.abs((int)(image.getHeight() * frame.getV2() - frame.getV1()));
				if(frameW>w)
					w = frameW;
				if(frameH>h)
					h = frameH;
			}
		}
		return new Dimension(w, h);
	}
	
	private Image findImageByName(String imageName) {
		for (Image image : nanim.getImagesList()) {
			if (image.getName().equals(imageName))
				return image;
		}
		return null;
	}

	public static void main(String[] args) throws ParseException, IOException {
		Options options = new Options();
		options.addOption("i", true, "input nanim file");

		if (args.length == 0) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("nanimenc [args]", options);
			return;
		}

		GnuParser parser = new GnuParser();
		CommandLine line = parser.parse(options, args);

		new NanimView(line);
	}

	private void decode() throws IOException {
		File inputFile = new File(commandLine.getOptionValue("i"));
		FileInputStream is = new FileInputStream(inputFile);
		try {
			nanim = NanimParser.Nanim.parseFrom(is);
		} finally {
			is.close();
		}

	}

	private void load() throws IOException {
		for (Image image : nanim.getImagesList()) {
			loadImage(image);
		}
	}

	private void loadImage(Image image) throws IOException {
		BufferedImage loadedImage = null;
		switch (image.getFormat()) {
		case RGBA:
			loadedImage = new BufferedImage(image.getWidth(),
					image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			setRgba(loadedImage, image);
			break;
		case RGB:
			loadedImage = new BufferedImage(image.getWidth(),
					image.getHeight(), BufferedImage.TYPE_INT_RGB);
			setRgb(loadedImage, image);
			break;
		}
		loadedBufferedImages.put(image.getName(), loadedImage);
	}

	private void setRgba(BufferedImage outputImage, Image image) {
		int w = image.getWidth();
		int h = image.getHeight();
		ByteString pixels = image.getPixels();
		int pixelIndex = 0;
		for (int x = 0; x < w; ++x) {
			for (int y = 0; y < h; ++y) {
				byte r = pixels.byteAt(pixelIndex++);
				byte g = pixels.byteAt(pixelIndex++);
				byte b = pixels.byteAt(pixelIndex++);
				byte a = pixels.byteAt(pixelIndex++);

				// ImageIO is just plain stupid...
				if (a == 0) {
					a = 1;
				}

				int rgba = (a << 24) + (r << 16) + (g << 8) + b;
				outputImage.setRGB(x, y, rgba);
			}
		}
	}

	private void setRgb(BufferedImage outputImage, Image image) {
		int w = image.getWidth();
		int h = image.getHeight();
		ByteString pixels = image.getPixels();
		int pixelIndex = 0;
		for (int x = 0; x < w; ++x) {
			for (int y = 0; y < h; ++y) {
				int r = pixels.byteAt(pixelIndex++);
				int g = pixels.byteAt(pixelIndex++);
				int b = pixels.byteAt(pixelIndex++);
				int rgba = (r << 16) + (g << 8) + b;
				outputImage.setRGB(x, y, rgba);
			}
		}
	}
}

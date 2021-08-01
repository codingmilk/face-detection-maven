import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.processing.face.detection.DetectedFace;
import org.openimaj.image.processing.face.detection.HaarCascadeDetector;

public class FaceDetector extends JFrame {

    public BufferedImage readImg(String filePath) {
        BufferedImage image;
        File f;

        //read image
        try {
            f = new File(filePath); //image file path
            image = ImageIO.read(f);
        } catch (IOException e) {
            System.err.println(e);
            return null;
        }
        return image;
    }

    private static final long serialVersionUID = 1L;
    private static final HaarCascadeDetector detector = new HaarCascadeDetector();
    private final BufferedImage img;
    private List<DetectedFace> faces;

    public FaceDetector(String[] a) {
        img = readImg(a[0]);
        ImagePanel panel = new ImagePanel(img);
        add(panel);
        setTitle("Face Recognizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void showFace() {
        JFrame fr = new JFrame("Discovered Faces");

        if (!detectFaceToShow(fr)) return;

        fr.setLayout(new FlowLayout(FlowLayout.LEFT));
        fr.setSize(500, 500);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setVisible(true);
    }

    private boolean detectFaceToShow(JFrame fr) {
        // Add face detection functionality here
        faces = detector.detectFaces(ImageUtilities.createFImage(img));

        if (faces == null) {
            System.out.println("No faces found in the captured image");
            return false;
        }

        for (DetectedFace face : faces) {
            FImage faceImg = face.getFacePatch();
            ImagePanel p = new ImagePanel(ImageUtilities.createBufferedImage(faceImg));
            fr.add(p);
        }
        return true;
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            FaceDetector fd = new FaceDetector(args);
            fd.showFace();
        } else {
            System.out.println("run imageFile.jpg");
        }
    }
}

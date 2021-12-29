package core;

import java.awt.Color;
import java.io.File;
import java.net.URISyntaxException;
import javax.swing.ImageIcon;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_dnn_superres.DnnSuperResImpl;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;


public class Upscale {
    private Upscale(){}

    public static boolean run(String loadPath, String savePath) {
        String[] mode = MainApp.getMode();
        Utils.write("Started Upscale Process ["+mode[0]+"]", null);
        ImageIcon s = new ImageIcon(loadPath);
        int width = s.getIconWidth(); 
        int height = s.getIconHeight();
        String originalSize="["+width+"x"+height+"]";      
        width *= Integer.valueOf(mode[2]);
        height *= Integer.valueOf(mode[2]);
        String newSize = "["+width+"x"+height+"]";
        if(width > 6666 || height > 6666) {
            Utils.write("ERROR: Expected output has a side thats bigger than 6666 pixels", Utils.SCARLET);
            return false;
        }


        //should never happen
        if (mode[0].equals("ERROR")) {
            Utils.write("Error Loading Config", Utils.SCARLET);
            return false;
        }

        //no savePath option
        if(savePath == null) {
            StringBuilder sb = new StringBuilder(loadPath);
            savePath = sb.insert(sb.lastIndexOf("."),"("+mode[0]+")").toString();
        }


        Utils.write("Loading Image",null);

        Mat image = imread(loadPath);
        if (image.empty()) {
            Utils.write("Error Loading Image",Utils.SCARLET);
            return false;
        }
        String modelName = "Models/"+mode[0]+".pb";
        Mat imageNew = new Mat();
        Utils.write("Loading AI",null);
        DnnSuperResImpl sr = null;
            try {
                sr = new DnnSuperResImpl();
                   File modelPath = new File(new File(Upscale.class.getProtectionDomain().getCodeSource().getLocation()
                        .toURI().getPath()).getParent()+File.separator+modelName);
                    if (!modelPath.exists()) {
                        Utils.write("Model not found!",Utils.SCARLET);
                        return false;
                    }
                Utils.write("Trying to read model from "+modelPath,null);
                sr.readModel(modelPath.toString());
                sr.setModel(mode[1], Integer.valueOf(mode[2]));
                Utils.write("""
                            Algorithm and Size Checked
                             \t Starting conversion""",null);
                sr.upsample(image, imageNew);
                
                if(imageNew.empty()){
                    Utils.write("Error UpScaling !",Utils.SCARLET);
                    return false;
                }          
                Utils.write("Image was successfully upScaled from "+originalSize+"x"+mode[2]+", to "+newSize+"and saved to:",null);
                Utils.write(savePath,Utils.SVGBLUE);
                Config.FIELD02.setValue(mode[0]);
                imwrite(savePath, imageNew);
                return true;
            } catch(NumberFormatException | URISyntaxException e) {
                Utils.write("Error UpScaling !",Utils.SCARLET);
                Utils.write(e.getMessage(), Color.yellow);
                return false;
            }
                finally {
                    imageNew.release();
                    sr.deallocate();
                    sr.close();
            }
        
    }
}


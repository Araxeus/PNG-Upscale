package core;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_dnn_superres.DnnSuperResImpl;

import static org.bytedeco.opencv.global.opencv_imgcodecs.*;

import java.io.File;
import javax.swing.ImageIcon;

@SuppressWarnings({"java:S106","java:S2093"})
public class Upscale {
    private Upscale(){}

    public static boolean run(String loadPath, String savePath) {
        String[] mode = MainApp.getMode();
        MainApp.write("Started Upscale Process ["+mode[0]+"]", null);
        ImageIcon s = new ImageIcon(loadPath);
        int width = s.getIconWidth(); 
        int height = s.getIconHeight();
        String originalSize="["+width+"x"+height+"]";      
        width *= Integer.valueOf(mode[2]);
        height *= Integer.valueOf(mode[2]);
        String newSize = "["+width+"x"+height+"]";
        if(width > 6666 || height > 6666) {
            MainApp.write("ERROR: Expected output has a side thats bigger than 6666 pixels", MainApp.SCARLET);
            return false;
        }


        //should never happen
        if (mode[0].equals("ERROR")) {
            MainApp.write("Error Loading Config", MainApp.SCARLET);
            return false;
        }

        //no savePath option
        if(savePath == null) {
            StringBuilder sb = new StringBuilder(loadPath);
            savePath = sb.insert(sb.lastIndexOf("."),"("+mode[0]+")").toString();
        }


        MainApp.write("Loading Image",null);
        Mat image = imread(loadPath);
        if (image.empty()) {
            MainApp.write("Error Loading Image",MainApp.SCARLET);
            return false;
        }
        String modelName = "Models/"+mode[0]+".pb";
        Mat imageNew = new Mat();
        MainApp.write("Loading AI",null);
        DnnSuperResImpl sr = null;
            try {
                sr = new DnnSuperResImpl();
                   File modelPath = new File(new File(Upscale.class.getProtectionDomain().getCodeSource().getLocation()
                        .toURI().getPath()).getParent()+File.separator+modelName);
                    if (!modelPath.exists()) {
                        MainApp.write("Model not found!",MainApp.SCARLET);
                        return false;
                    }
                MainApp.write("Trying to read model from "+modelPath,null);
                sr.readModel(modelPath.toString());
                sr.setModel(mode[1], Integer.valueOf(mode[2]));
                MainApp.write("Algorithm and Size Checked"+"\n \t Starting conversion",null);
                sr.upsample(image, imageNew);
                
                if(imageNew.isNull()){
                    MainApp.write("Error UpScaling !",MainApp.SCARLET);
                    return false;
                }          
                MainApp.write("Image was successfully upScaled from "+originalSize+"x"+mode[2]+", to "+newSize+"and saved to:",null);
                MainApp.write(savePath,MainApp.SVGBLUE);
                Config.FIELD02.setValue(mode[0]);
                imwrite(savePath, imageNew);
                return true;
            } catch(Exception e) {
                MainApp.write("Error UpScaling !",MainApp.SCARLET);
                e.printStackTrace();
                return false;
            }
                finally {
                    imageNew.close();
                    sr.deallocate();
                    sr.close();
            }
        
    }
}


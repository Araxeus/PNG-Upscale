package core;

import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_dnn_superres.DnnSuperResImpl;

import static org.bytedeco.opencv.global.opencv_imgcodecs.*;

import java.io.File;

@SuppressWarnings({"java:S106"})
public class Upscale {
    private Upscale(){};

    @SuppressWarnings({"java:S2095","java:S2093"})
    public static void run(String loadPath, String savePath) {

        String[] mode = MainApp.getMode();

        //should never happen
        if (mode[0].equals("ERROR")) {
            MainApp.write("Error Loading Config", MainApp.SCARLET);
            return;
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
            return;
        }
        String modelName = "Models/"+mode[0]+".pb";
        Mat imageNew = new Mat();
        MainApp.write("Loading AI",null);
        DnnSuperResImpl sr = null;
            try {
                sr = new DnnSuperResImpl();
                //File modelPath = new File(Upscale.class.getClassLoader().getResource(modelName).toURI());
                   File modelPath = new File(new File(Upscale.class.getProtectionDomain().getCodeSource().getLocation()
                        .toURI().getPath()).getParent()+File.separator+modelName);
                    if (!modelPath.exists()) {
                        MainApp.write("Model not found!",MainApp.SCARLET);
                        return;
                    }
                MainApp.write("Trying to read model from "+modelPath,null);
                sr.readModel(modelPath.toString());
                sr.setModel(mode[1], Integer.valueOf(mode[2]));
                MainApp.write("Algorithm and Size Checked"+"\n \t Starting conversion",null);
                sr.upsample(image, imageNew);
                
                if(imageNew.isNull()){
                    MainApp.write("Error UpScaling !",MainApp.SCARLET);
                    return;
                }

                MainApp.write("Image was successfully upScaled by a Factor of x"+mode[2]+" and saved to:",null);
                MainApp.write("\t"+savePath,MainApp.SVGBLUE);
                Config.FIELD02.setValue(mode[0]);
                imwrite(savePath, imageNew);
            } catch(Exception e) {
                MainApp.write("Error UpScaling !",MainApp.SCARLET);
                e.printStackTrace();
            }
                finally {
                    imageNew.close();
                    sr.deallocate();
                    sr.close();
            }
        
    }
}


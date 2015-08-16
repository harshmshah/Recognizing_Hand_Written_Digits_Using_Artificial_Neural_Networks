

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.pow;
import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import static sun.font.GlyphLayout.done;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author harshmshah
 */


public class ArtificialNeuralNetwork {
    static double network[][]=new double[784][550];  //network between input layer and Hidden Layer
    static double network_layer2[][]=new double[550][10]; //network between hidden layer and output
    static double input[]=new double[784]; //activation values for input layer
    static double act_hidden[]=new double[550]; //activation values for hidden layer nodes
    
    static double act_output[]=new double[10];  // activation values for output layer nodes
    
    
    
    public static void main(String[] args) throws IOException {
		//String dir = "/Users/harshmshah/USC/CSCI 561 AI/HW4/";
        ImageData im=new ImageData();
		ArrayList<ImageData> imagesTrain = im.readData(args[0]+".idx3-ubyte",args[1]+".idx1-ubyte");
		//ArrayList<ImageData> imagesTrain2 = im.readData(dir + "train-images.idx3-ubyte", dir + "train-labels.idx1-ubyte");
        ArrayList<ImageData> imagesTest = im.readData(args[2]+".idx3-ubyte",null);
        BufferedWriter bw= new BufferedWriter(new FileWriter(args[3]));
        imagesTrain.get(0).drawImage();
        imagesTest.get(0).drawImage();
        // System.out.println(imagesTrain.get(29).data[0].toString());
        int done=1;
        int load=0;
        int images_count=0;
        int images_test_count=0;
        int hit=0;
        int initial=0;
        int learning_rate=1;
        
        
        
        for(int epoch=1;epoch<=1;epoch++)
        {
            
            images_count=0;
            
            for(ImageData img:imagesTrain)  //for each of the images
            {
                
                int image_label=((ImageData)imagesTrain.get(images_count)).label;
                images_count++;
                
                // System.out.println("Image count "+images_count+"Images Label" + image_label);
                int count=0;
                
                
                int label_values[]=new int[10];     //for label values array for comparing output with label
                for(int i=0;i<10;i++)
                {
                    label_values[i]=0;
                }
                
                label_values[image_label]=1;
                
                //System.out.println(Arrays.toString(label_values));
                
                for(int i=1;i<img.data.length;i++) //putting input data in first layer
                {
                    for(int j=1;j<img.data[0].length;j++)
                    {
                        input[count++]=((img.data[i][j])/100);
                    }
                }
                // System.out.println(Arrays.toString(input));
                if(initial==0)
                {
                    initial++;
                    double randomvalues[]={0.0001,0.0,-0.0001,0.00012,0.00027,-0.00005};
                    Random value=new Random();
                    for(int i=0;i<784;i++)     //first step of algorithm.Assigning Random weights to layer 1
                    {
                        for(int j=0;j<550;j++)
                        {
                            //  System.out.println("omrand");
                            //double wt=((value.nextDouble())*(1.0-(-1.0)))+(-1.0);
                            double wt=((value.nextDouble())*(0.1-(-0.1)))+(-0.1);
                            //double wt=(value.nextGaussian());
                            network[i][j]=wt;
                            //network[i][j]=value.nextInt(6);
                        }
                    }
                    
                    for(int i=0;i<784;i++)
                    {
                        for(int j=0;j<550;j++)
                        {
                            //System.out.print(" "+network[i][j]);
                        }
                        // System.out.println("");
                    }
                    
                    double randomvalues2[]={-0.45,-0.3,0.0,0.2,0.4,0.16};
                    Random value2=new Random();
                    for(int i=0;i<550;i++)     //first step of algorithm.Assigning Random weights to layer 2
                    {
                        for(int j=0;j<10;j++)
                        {
                            double wt=((value2.nextDouble())*(0.1-(-0.1)))+(-0.1);
                            //  double wt=((value2.nextDouble())*(1.0-(-1.0)))+(-1.0);
                            //double wt=(value2.nextGaussian());
                            network_layer2[i][j]=wt;
                            //network_layer2[i][j]=randomvalues2[value2.nextInt(6)];
                            //network[i][j]=value.nextInt(6);
                        }
                    }
                }
                
                double btw=0;
                for(int i=0;i<550;i++)    //Feed-Forward for Layer 1
                {   btw=0;
                    for(int j=0;j<784;j++)
                        
                    {
                        
                        btw=btw+input[j]*network[j][i];
                        
                        
                    }
                    act_hidden[i]=(1/(1+Math.exp(-btw)));
                    // act_hidden[i]=(1/(1+(pow(2.718,(-btw)))));   //Getting Activation values for hidden layer
                    
                    
                }
                
                double btw1=0;
                for(int i=0;i<10;i++)    //Feed-Forward for Layer 2
                {   btw1=0;
                    for(int j=0;j<550;j++)
                        
                    {
                        
                        btw1=btw1+act_hidden[j]*network_layer2[j][i];
                        
                        
                    }
                    //double exp=btw*input[i];
                    act_output[i]=(1/(1+Math.exp(-btw1)));
                    //act_output[i]=(1/(1+(pow(2.718,(-btw1)))));   //Getting Activation values for output layer
                    
                    
                }
                
                //Back-Propogation
                
                //Computing Delta Values
                double delta_layer2[]=new double[10];
                double delta_layer1[]=new double[550];
                
                ImageData img1;
                //int x=img1.label;
                
                
                
                
                for(int i=0;i<10;i++)   //Computing deltas for output layer
                {
                    
                    // label_values[x]=1;
                    
                    
                    delta_layer2[i]=(label_values[i]-act_output[i])*(act_output[i]*(1-act_output[i]));
                    // System.out.println("x value"+x);
                    
                    
                }
                // System.out.println(Arrays.toString(delta_layer2));
                
                
                for(int i=0;i<550;i++)                              //Computing deltas for hidden layer
                {   double back=0;
                    for(int j=0;j<10;j++)
                    {
                        back=network_layer2[i][j]*delta_layer2[j]; //propogating delta backwards
                        
                    }
                    delta_layer1[i]=(act_hidden[i]*(1-act_hidden[i]))*back;  //Computing delta value for hidden layer
                }
                
                double delta_weight_l2[][]=new double[550][10];
                double delta_weight_l1[][]=new double[784][550];
                for(int i=0;i<550;i++)  //Calculating delta weights for layer 2
                {
                    for(int j=0;j<10;j++)
                    {
                        delta_weight_l2[i][j]=0.05 * delta_layer2[j]*act_hidden[i];
                        // delta_weight_l2[i][j]=(1/(pow(2.718,(done)))) * delta_layer2[j]*act_hidden[i];
                    }
                }
                for(int i=0;i<784;i++)  //Calculating delta weights for layer 1
                {
                    for(int j=0;j<550;j++)
                    {
                        delta_weight_l1[i][j]=0.05*delta_layer1[j]*input[i];
                        //delta_weight_l1[i][j]=(1/(pow(2.718,(done))))*delta_layer1[j]*input[i];
                    }
                }
                
                for(int i=0;i<784;i++)                          //updating weights for layer 1
                {
                    for(int j=0;j<550;j++)
                    {
                        network[i][j]=network[i][j]+delta_weight_l1[i][j];
                    }
                }
                for(int i=0;i<550;i++)                        //updating weights for layer 2
                {
                    for(int j=0;j<10;j++)
                    {
                        network_layer2[i][j]=network_layer2[i][j]+delta_weight_l2[i][j];
                    }
                }
                
                
                /*if( (done%100)==0 )
                 {
                 System.out.println("loading"+load+"%");
                 
                 load++;
                 }*/
                //System.out.println(done);
                
                
                done++;
                
                
            } //end of all images set
            
            
            
            //end of all images set
            
            
            //Testing Images
            
            
        }
        
        
        for(ImageData img:imagesTest)
        {      int count=0;
            for(int i=1;i<img.data.length;i++) //putting input data in first layer
            {
                for(int j=1;j<img.data[0].length;j++)
                {
                    input[count++]=((img.data[i][j])/100);
                }
            }
            
            double btw=0;
            for(int i=0;i<550;i++)    //Feed-Forward for Layer 1
            {   btw=0;
                for(int j=0;j<784;j++)
                    
                {
                    
                    btw=btw+input[j]*network[j][i];
                    
                    
                }
                act_hidden[i]=(1/(1+Math.exp(-btw)));
                // act_hidden[i]=(1/(1+(pow(2.718,(-btw)))));   //Getting Activation values for hidden layer
                
                
            }
            
            double btw1=0;
            for(int i=0;i<10;i++)    //Feed-Forward for Layer 2
            {   btw1=0;
                for(int j=0;j<550;j++)
                    
                {
                    
                    btw1=btw1+act_hidden[j]*network_layer2[j][i];
                    
                    
                }
                //double exp=btw*input[i];
                act_output[i]=(1/(1+Math.exp(-btw1)));
                //act_output[i]=(1/(1+(pow(2.718,(-btw1)))));   //Getting Activation values for output layer
                
                
            }
            // System.out.println("Output activation");
            //System.out.println(Arrays.toString(act_output));
            
            
            
            //System.out.println("Image count "+images_test_count);
            
            
            
            //System.out.println("Desired TEst output");
            //System.out.println(Arrays.toString(label_values));
            
            double max=act_output[0];
            int index=0;
            
            for(int i=0;i<10;i++)
            {
                
                if(max<act_output[i])
                {
                    max=act_output[i];
                    index=i;
                }
            }
            
            //System.out.println("Network Value"+index + "Desired Value" + image_label);
        
                bw.write(index+"\n");
                //hit++;
            
            
            
        } //end of Test Images
        
              
        
        
        bw.close();     
        
    }
}


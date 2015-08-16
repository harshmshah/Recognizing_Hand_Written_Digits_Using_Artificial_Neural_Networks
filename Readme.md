Name: Harsh M. Shah

In this project I have created an artificial neural network to identify images of handwritten digits.Neural networks are a machine learning tool inspired by the brain. I have implemented a simple neural network using fully-connected layers, and train using the backpropagation algorithm. 

Here’s the breakdown of what my program does:• Construct/initialize a neural network• Read the training data• Use the training data to train the network (as described above)• Read the testing data• Evaluate the testing data using your network• Output a text file containing the predictions for the testing data

Please ensure that your program does not takes ~5 minutes to perform all of these tasks.

Input :
Input will be images of handwritten digits (0-9). All images will be 28x28, with each pixel having a greyscale value between 0-255. The images are all stored in a single binary file. The labels will be a list of integers, representing which digit is being shown in the image. The labels are all stored in a separate binary file. Both of these files begin with a few metadata integer values (number of images, number of rows/columns, etc.). 

Output :
Simply a list of the predicted values of each of the test images, as decided by the network. All this values are saved in output.txt. 

Execution :
javac ArtificialNeuralNetwork.java
java artificialneuralnetwork train-images train-labels test-images output.txt 

Accuracy Achieved : 85%


P.S. Please ensure that your program does not takes ~5 minutes to perform all of these tasks.

# Nine Men' Morris
Sprint 3 Demo: https://youtu.be/z88bg2aQVc4

## Introduction
Program is developed using Java and JavaFX.


## How to run the game
1. Download the latest release from [here](https://git.infotech.monash.edu/fit3077-s1-2023/MA_Thursday4pm_Team6/project/-/tree/main/Sprint%203/Releases).

2. There are 2 versions, a **ZIP file** containing a Windows Batch File and a **JAR file**:
   - For Windows: (recommended if you are using Windows)
     - unzip <'filename'>, locate the directory of the .bat file and run it. 
   - For Mac or Windows:
     - you will need Java 17 or above installed. 
     - Run the .jar file by double clicking on it or run it from the terminal in the folder using the command `java -jar 9mm.jar`


## How to compile and run the build on intelliJ
**To build our 9MM game, please follow the steps below:**
1. Clone the GitLab repository
2. Set up JavaFX in Intellij. You may refer this [video](https://www.google.com/url?sa=t&rct=j&q=&esrc=s&source=web&cd=&cad=rja&uact=8&ved=2ahUKEwiAxeq42Mn-AhXk1TgGHWZkCUAQwqsBegQICBAF&url=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3DIvsvjUq38Jc&usg=AOvVaw0gUcKQI4-TAIw965WQCYux) or JavaFX's [website](https://openjfx.io/openjfx-docs/) if necessary.
3. Set up VM configuration for JavaFX (shown in last part of video) and run from there.

If the game runs successfully, you will see the game interface.

## Rules implemented so far
1. All basic rules have been implemented.

## Dependencies to build the project
- Java JDK 17.0.7
- JavaFX 17
- JavaFX Scene Builder (optional)

## Troubleshoot
If you face the error setting up JavaFX in intelliJ as shown below, it is likely that you have not set up JavaFX path correctly. Kindly update your path according to 
where you saved your JavaFX SDK.
![troubleshoot.png](res%2Ftroubleshoot.png)

This configuration can be found in the VM options of the Run/Debug Configurations.
![troubleshoot1.png](res%2Ftroubleshoot1.png)

## Generate JAR executable  file

**Instructions**

1. In IntelliJ, click on the **Maven** symbol in the toolbar at the right side of the screen.
2. Click on **Lifecycle** to expand it, and then double click on **package**.
3. Wait for Maven to build and package the JAR.
4. After you get the "BUILD SUCCESS" message, go to the target folder to access the generated JAR file.
5. Run the JAR file. There might be some warnings in the consoles, but they should be harmless.
6. Happy gaming!

**Video Tutorial**

![](res/generate-jar-maven.mp4)



## Generate BAT file

**Instructions**

1. Open Nine_Men_Morris_Maven in intelliJ
2. Load project using the Maven pop up on bottom left
3. In IntelliJ, click on the **Maven** symbol in the toolbar at the right side of the screen.
4. Click on Nine_Men_Morris > plugins > compiler > compiler:compile to compile the code
5. Click on plugins > javafx > javafx:jlink to create an app.zip in target folder


## Screenshots
<img src="res/9mm_cover.png" alt="9MM Main menu Screenshot"/>
<img src="res/ingame_screenshot.png" alt="9MM in game Screenshot"/>





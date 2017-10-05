Checkstyle Project - Structural Metrics
==============

CptS 422 - Fall 2017
--------------

- Alexander Lao
- Minh Nguyen
- Eunji Park

USAGE
--------------
The paths to the jar files will need to be updated when running the project locally.

After the project has been cloned locally and imported to your Eclipse workspace:
1. Right click on the "net.sf.eclipsecs.sample" project and go to "Properties."
2. Click on "Java Build Path" on the left column.
3. Click on the "Libraries" tab.
4. Remove all the jar files that have path issues by highlighting them and clicking "Remove."
5. Click on "Add External Jars."
6. Traverse to "eclipse-cs-git/net.sf.eclipsecs.sample/Lib" and add every jar in that directory.

To run the JUnit tests:
1. Traverse to "net.sf.eclipsecs.sample/src/testing/TestMethodLimit.java."
2. Right click on the "TestMethodLimit.java" file and go to "Run As -> JUnit Test."

To run the Structural Metric CheckStyle on any project:
1. Right click on the "net.sf.eclipsecs.sample" project and go to "Run As -> Eclipse Application."
2. Import the project you want to run our CheckStyle checks on in the new Eclipse instance.
3. Click on "Window -> Preferences" on the top menu bar.
4. Click on "Checkstyle" on the left column.
5. Click on "New" to create a new Checkstyle configuration.
6. Keep the type as "Internal Configuration" and give it a name. Click "OK."
7. Click on the newly created Checkstyle configuration and click on "Set as Default."
8. While still highlighted on the newly created Checkstyle configuration, click on "Configure."
9. Scroll down and click on "My custom checks" on the left column. You should see "Structural Metrics" underneath "My customm checks."
10. Click on "Add" while "Structural Metrics is highlighted.
11. Change the severity to "error" and click "OK."
12. Right click on the .class file you want to check and click on "Checkstyle -> Check code with Checkstyle."
13. You will see the Structural Metric checks in the "Problems" console under "Errors."

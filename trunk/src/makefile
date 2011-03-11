BIN = ../WEB-INF/classes/
UTIL= util/
LOGIN= loginModule/
UPLOAD= upload_image/
DOWNLOAD= display_image/
CLASSPATH=$CLASSPATH\:/oracle/jdbc/lib/classes12.zip:/usr/local/catalina/lib/servlet-api.jar:/home/dsk01/ugrad/narzt/catalina/webapps/proj1/WEB-INF/lib/commons-fileupload-1.0.jar
export CLASSPATH

all: ConnectionManager.class UploadImage.class PictureBrowse.class LoginModule.class

ConnectionManager.class: $(UTIL)ConnectionManager.java
	javac -d $(BIN) $(UTIL)ConnectionManager.java

LoginModule.class: $(LOGIN)LoginModule.java
	javac -d $(BIN) $(LOGIN)LoginModule.java 

UploadImage.class: $(UPLOAD)UploadImage.java
	javac -d $(BIN) $(UPLOAD)UploadImage.java

PictureBrowse.class: $(DOWNLOAD)PictureBrowse.java $(BIN)GetOnePic.class
	javac -d $(BIN) $(DOWNLOAD)PictureBrowse.java

$(BIN)GetOnePic.class: $(DOWNLOAD)GetOnePic.java
	javac -d $(BIN) $(DOWNLOAD)GetOnePic.java

clean:
	rm -f $(BIN)*.class

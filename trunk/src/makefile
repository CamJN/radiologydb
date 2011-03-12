BIN = ../WEB-INF/classes/
UTIL= util/
LOGIN= loginModule/
UPLOAD= upload_image/
DOWNLOAD= display_image/

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

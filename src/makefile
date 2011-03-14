
BIN = ../WEB-INF/classes
LIB = ../WEB-INF/libs
UTIL= util/
LOGIN= loginModule/
UPLOAD= upload_image/
DOWNLOAD= display_image/

CPATH := $$CLASSPATH:$(BIN):$(LIB)/*:

all: ConnectionManager.class UploadImage.class PictureBrowse.class LoginModule.class

ConnectionManager.class: $(UTIL)ConnectionManager.java
	javac -cp $(CPATH) -d $(BIN) $(UTIL)ConnectionManager.java

LoginModule.class: $(LOGIN)LoginModule.java
	javac -cp $(CPATH) -d $(BIN) $(LOGIN)LoginModule.java 

UploadImage.class: $(UPLOAD)UploadImage.java
	javac -cp $(CPATH) -d $(BIN) $(UPLOAD)UploadImage.java

PictureBrowse.class: $(DOWNLOAD)PictureBrowse.java $(BIN)GetOnePic.class
	javac -cp $(CPATH) -d $(BIN) $(DOWNLOAD)PictureBrowse.java

$(BIN)GetOnePic.class: $(DOWNLOAD)GetOnePic.java 
	javac -cp $(CPATH) -d $(BIN) $(DOWNLOAD)GetOnePic.java

clean:
	rm -f $(BIN)*.class

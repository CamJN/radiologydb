BIN = ../WEB-INF/classes
LIB = ../WEB-INF/lib
UTIL= util/
LOGIN= loginModule/
SEARCH= search/
UPLOAD= upload_image/
DOWNLOAD= display_image/

JAVAFILES := ${shell find -name '*.java' -type f -print}
CPATH := $$CLASSPATH:$(BIN):$(LIB)/*:

compile: $(JAVAFILES)
	@echo COMPILE
	@javac -d $(BIN) -cp $(CPATH) $(JAVAFILES)

clean:
	rm -f $(BIN)*.class

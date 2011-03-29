BIN = ../WEB-INF/classes
LIB = ../WEB-INF/lib
UTIL= util/
LOGIN= loginModule/
SEARCH= search/
UPLOAD= upload_image/
DOWNLOAD= display_image/
CREATE= create_record/

JAVAFILES := ${shell find -name '*.java' -type f -print}
CPATH := $$CLASSPATH:$(BIN):$(LIB)/*:

compile: $(JAVAFILES)
	@echo COMPILE
	touch ../WEB-INF/web.xml
	@javac -d $(BIN) -cp $(CPATH) $(JAVAFILES)

clean:
	rm -rf $(BIN)/*

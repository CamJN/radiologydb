BIN = ../WEB-INF/classes/
UTIL= util/
LOGIN= loginModule/

all: ConnectionManager.class LoginModule.class

ConnectionManager.class: $(UTIL)ConnectionManager.java
	javac -d $(BIN) $(UTIL)ConnectionManager.java

LoginModule.class: $(LOGIN)LoginModule.java
	javac -d $(BIN) $(LOGIN)LoginModule.java

clean:
	rm -f $(BIN)*.class

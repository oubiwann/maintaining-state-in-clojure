VERSION=$(shell (head -1 project.clj |awk '{print $$3}'|sed -e 's/"//g'))
PROJECT=maintaining-state-in-clojure
LIB=state-examples
JAR=target/$(PROJECT)-$(VERSION).jar
STANDALONE_JAR=target/$(PROJECT)-$(VERSION)-standalone.jar
BIN_DIR=/usr/local/bin

clean:
	rm -rf target/*.jar

$(BIN_DIR)/lein-exec:
	wget https://raw.github.com/kumarshantanu/lein-exec/master/lein-exec
	chmod a+x lein-exec
	clear
	@echo "Preparing to move lein-exec into /usr/local/bin ..."
	@read
	sudo mv lein-exec /usr/local/bin

$(BIN_DIR)/lein-exec-p:
	wget https://raw.github.com/kumarshantanu/lein-exec/master/lein-exec-p
	chmod a+x lein-exec-p
	clear
	@echo "Preparing to move lein-exec and lein-exec-p into /usr/local/bin ..."
	@read
	sudo mv lein-exec-p /usr/local/bin

script-setup: $(BIN_DIR)/lein-exec $(BIN_DIR)/lein-exec-p

build: clean
	@lein compile
	@lein uberjar

shell:
	@lein repl

dev:
	@lein run --development

run:
	@lein run

run-jar: build
	java -jar $(JAR)

run-jar-standalone: build
	java -jar $(STANDALONE_JAR)

kibit-only:
	@lein with-profile testing kibit

test-only:
	@lein with-profile testing test

coverage-only:
	@lein with-profile testing cloverage --text --html
	@cat target/coverage/coverage.txt
	@echo "body {background-color: #000; color: #fff;} \
	a {color: #A5C0F0;}" >> target/coverage/coverage.css

check-versions:
	@echo "Version Info"
	@echo "------------"
	@echo
	@echo "Makefile:"
	@echo "\t$(VERSION)"
	@echo "project.clj:"
	@lein exec -e '(println (str "\t" (last (clojure.string/split (first (clojure.string/split-lines (slurp "project.clj"))) #"\s+"))))'|sed -e 's/"//g'
	@echo "$(LIB).version:"
	@lein exec -ep "(require '[$(LIB).version]) (print (str \"\t\" $(LIB).version/version-str))"

check: kibit-only test-only coverage-only check-versions

upload:
	@lein deploy clojars

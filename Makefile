.PHONY: all test clean

clean:
	rm -rf target/*

repl:
	clojure -M:nrepl:dev:test

lint:
	clojure -M:dev -m clj-kondo.main --lint src/**

test:
	clojure -M:test -m kaocha.runner

build:
	mkdir -p ./target/public
	cp -r ./resources/public/* ./target/public
	clojure -M:build

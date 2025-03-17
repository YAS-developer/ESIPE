CFLAGS=-Wall
GENFILES=random.txt buffered unbuffered

.PHONY: test
test: $(GENFILES)
	@echo "--"
	time -p ./buffered <random.txt >/dev/null
	@echo "--"
	time -p ./unbuffered <random.txt >/dev/null

random.txt: makefile
	tr -dc '\n a-zA-Z' </dev/urandom | head -c 10M >random.txt

.PHONY: clean
clean:
	rm -f $(GENFILES)


CFLAGS=-Wall
GENFILES=pipeline-modern pipeline-bourne pipeline-staircase
PIPELINE=./ps-forest more less tee cat

.PHONY: test
test: $(GENFILES)
	@chmod u+x ps-forest
	@printf "\n== Pipeline architecture used by most modern shells ==\n"
	./pipeline-modern $(PIPELINE)
	@printf "\n== Pipeline architecture used by the classic Bourne shell ==\n"
	./pipeline-bourne $(PIPELINE)
	@printf "\n== Alternative pipeline architecture in the form of a staircase ==\n"
	./pipeline-staircase $(PIPELINE)

.PHONY: clean
clean:
	rm -f $(GENFILES)

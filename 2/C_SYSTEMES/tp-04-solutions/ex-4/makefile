ARGS=-n mshow.c mrun.c

all: test_mshow test_mrun

test_mshow: mshow
	@printf "\n== Question 1 ==\n"
	env RUN_0=ls RUN_1=cat RUN_3=stat ./mshow

test_mrun: mrun
	@printf "\n== Question 2 ==\n"
	env RUN_0=ls RUN_1=cat RUN_3=stat ./mrun $(ARGS)

all: test_fork-zombie test_fork-wait

test_fork-zombie: fork-zombie
	@printf "\n== Question 2 ==\n"
	./fork-zombie &
	ps --forest
	pkill fork-zombie

test_fork-wait: fork-wait
	@printf "\n== Question 3 ==\n"
	./fork-wait

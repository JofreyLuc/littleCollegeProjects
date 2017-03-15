CC=gcc
CFLAGS=-W -Wall -Wextra -Wfloat-equal -Wwrite-strings -Wpointer-arith -Wuninitialized -Winit-self -Wundef -Wstrict-prototypes -Wunreachable-code -Waggregate-return -Wswitch-default -Wswitch-enum
LDFLAGS=-lm

EXEC=jeu

all: $(EXEC)

$(EXEC): $(EXEC).c
	$(CC) $(CFLAGS) $^ -o $@ $(LDFLAGS)

clean:
	rm -rf $(EXEC)
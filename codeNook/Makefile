CXX=g++
CXXFLAGS=-W -Wall -Wextra# -Wconversion -Wfloat-equal -Wwrite-strings -Wpointer-arith -Wuninitialized -Winit-self -Wundef -Wmissing-declarations -Wunreachable-code -Waggregate-return -Wswitch-default -Wswitch-enum
LDFLAGS=-Iinclude

SRC=$(wildcard src/*.cpp)
OBJ=$(SRC:.cpp=.o)

EXEC=main
PREFIX=/usr

all: $(EXEC)

$(EXEC): $(OBJ) $(EXEC).cpp
	$(CXX) $(CXXFLAGS) $^ -o $@ $(LDFLAGS)

%.o: %.cpp
	$(CXX) $(CXXFLAGS) -c $< -o $@ $(LDFLAGS)

clean:
	rm src/*.o

mrproper: clean
	rm $(EXEC)
	rm main

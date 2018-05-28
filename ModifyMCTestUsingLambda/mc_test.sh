#! /bin/sh -v
cd bin

time java edu.umb.cs.cs681.hw06.MCTest 10000000000 1
time java edu.umb.cs.cs681.hw06.MCTest 10000000000 2
time java edu.umb.cs.cs681.hw06.MCTest 10000000000 4
time java edu.umb.cs.cs681.hw06.MCTest 10000000000 8
time java edu.umb.cs.cs681.hw06.MCTest 10000000000 16

#!/usr/bin/python
# -*- coding: utf-8 -*-

import os
from subprocess import Popen, PIPE

def solve_it(input_data):

    # Writes the inputData to a temporay file

    tmp_file_name = 'tmp.data'
    tmp_file = open(tmp_file_name, 'w')
    tmp_file.write(input_data)
    tmp_file.close()

    # Runs the command: java Solver -file=tmp.data

    process = Popen(['java', 'Solver', '-file=' + tmp_file_name], stdout=PIPE)
    (stdout, stderr) = process.communicate()

    # removes the temporay file
    os.remove(tmp_file_name)
#add .decode("utf-8") to address the problem of cross line in windows
#https://www.coursera.org/learn/discrete-optimization/discussions/weeks/2/threads/K2j5MD6NEeew7Q7A7m3f-g
    return stdout.strip().decode("utf-8")


import sys

if __name__ == '__main__':
    if len(sys.argv) > 1:
        file_location = sys.argv[1].strip()
        with open(file_location, 'r') as input_data_file:
            input_data = input_data_file.read()
        print(solve_it(input_data))
    else:
        print('This test requires an input file.  Please select one from the data directory. (i.e. python solver.py ./data/ks_4_0)')


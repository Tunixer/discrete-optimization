#!/usr/bin/python
# -*- coding: utf-8 -*-
import queue
q = queue.Queue()
def DFS(colorArr, Graph, Node, colorNum):
    if Node == len(colorArr):
        return True;
    for i in range(colorNum):
        if IsColored(colorArr, Graph, Node, i):
            colorArr[Node] = i
            q.put(i)
            temp = DFS(colorArr, Graph, Node+1, colorNum)
            if temp == True:
                return True
            colorArr[Node] = -1
            q.get()
    return False

'''
whether Node can be colored with colorIdx
'''
def IsColored(colarArr, Graph, Node, colorIdx):
    temp = Graph[Node]
    for col in temp:
        if colorIdx == colarArr[col]:
            return False;
    return True

def solve_it(input_data):
    # Modify this code to run your optimization algorithm

    # parse the input
    lines = input_data.split('\n')

    first_line = lines[0].split()
    node_count = int(first_line[0])
    edge_count = int(first_line[1])
    Graph = []
    colorArr = []
    for i in range(node_count):
        Graph.append([])
        colorArr.append(-1)

    edges = []
    for i in range(1, edge_count + 1):
        line = lines[i]
        parts = line.split()
        Graph[int(parts[0])].append(int(parts[1]))
        Graph[int(parts[1])].append(int(parts[0]))

    # build a trivial solution
    # every node has its own color
    ask = False
    if node_count < 1000:
        ask = DFS(colorArr, Graph, 0, 5)
    solution = []
    if ask == True:
        while not q.empty():
            solution.append(q.get())



    # prepare the solution in the specified output format
    output_data = str(node_count) + ' ' + str(0) + '\n'
    output_data += ' '.join(map(str, solution))

    return output_data


import sys

if __name__ == '__main__':
    import sys
    if len(sys.argv) > 1:
        file_location = sys.argv[1].strip()
        with open(file_location, 'r') as input_data_file:
            input_data = input_data_file.read()
        print(solve_it(input_data))
    else:
        print('This test requires an input file.  Please select one from the data directory. (i.e. python solver.py ./data/gc_4_1)')


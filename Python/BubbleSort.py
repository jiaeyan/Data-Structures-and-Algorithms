

# every pass move the max element in remaining array to the end
# sort in place
def bubble_sort_naive(array):
    swapped = True
    while swapped:
        swapped = False
        for i in range(len(array) - 1):
            if array[i] > array[i + 1]:
                array[i], array[i + 1] = array[i + 1], array[i]
                swapped = True


# no need to check the end compared elements in later loops
def bubble_sort_shrink(array):
    swapped = True
    loop = len(array) - 1
    while swapped:
        swapped = False
        for i in range(loop):
            if array[i] > array[i + 1]:
                array[i], array[i + 1] = array[i + 1], array[i]
                swapped = True
        loop -= 1


# set a flag on the position where swap happened
def bubble_sort_flag(array):
    loop = len(array) - 1
    while loop:
        flag = 0
        for i in range(loop):
            if array[i] > array[i + 1]:
                array[i], array[i + 1] = array[i + 1], array[i]
                flag = i + 1
        loop = flag



array = [8,4,6,1,2,9,5,3,7,0]
bubble_sort_flag(array)
print(array)
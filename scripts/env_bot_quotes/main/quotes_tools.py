from random import randint


def get_number_of_lines(filepath: str) -> int:
    """ Return the number of lines contained inside file. """
    number_of_lines = 0
    with open(filepath) as file:
        for line in file:
            number_of_lines += 1
    return number_of_lines


def get_line(filepath: str, line_number: int) -> str:
    """ Return the content of file at the line indicated by line_number. Return
        an empty string if line_number outreach the actual number of lines in
        file.
    """
    if line_number <= 0:
        return ""
    current_index = 0
    with open(filepath) as file:
        for line in file:
            current_index += 1
            if current_index == line_number:
                return line
    # If we are here, then the file has a number of lines inferior
    # to line_number
    return ""


def get_random_integer(floor: int, ceil: int) -> int:
    """ Return a random integer between 'floor' inclusive and 'ceil'
        exclusive.
    """
    if floor == ceil + 1:
        return floor
    elif floor >= ceil:
        raise ValueError("floor ({}) can't be greater or equals than"
                         " ceil ({})".format(floor, ceil))
    return randint(floor, ceil)


def get_random_quote(filepath: str, number_of_quotes_inside_file: int) -> str:
    return get_line(filepath,
                    get_random_integer(1, number_of_quotes_inside_file))


def main():
    FILE_QUOTES = "./quotes.txt"
    NUMBER_OF_QUOTES = get_number_of_lines(FILE_QUOTES)
    quote = get_random_quote(FILE_QUOTES, NUMBER_OF_QUOTES)
    print(quote)


if __name__ == '__main__':
    main()

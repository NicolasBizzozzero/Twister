import quotes_tools as qto
from time import sleep
from random import randint
from sys import argv


def print_usage():
    print("Usage: python bot_quotes.py connexion_key")


def main():
    if len(argv) != 2:
        print("Error: Wrong number of arguments")
        print_usage()
        return 1

    connexion_key = argv[1]
    FILE_QUOTES = "./quotes.txt"
    NUMBER_OF_QUOTES = qto.get_number_of_lines(FILE_QUOTES)
    WAITING_TIME_RANGE = (10, 60)

    while True:
        sleeping_time = randint(*WAITING_TIME_RANGE)
        sleep(sleeping_time)
        quote = qto.get_random_quote(FILE_QUOTES, NUMBER_OF_QUOTES)
        print(quote)


if __name__ == '__main__':
    main()

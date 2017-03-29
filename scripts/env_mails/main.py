"""Tools for sending mails server-side with our Apache-Tomcat WebApp "Twister"
You need to precise the mail of the receiver in your arguments, as well as the
type of mail you want to send. The available types are as follow:
- 1, Password Lost
- 2, Welcome Mail
- 3, Goodbye Mail
"""
from core import send_mail
from mail_templates import load_mail_template
from sys import argv
from getopt import getopt


def print_usage() -> None:
    print(("python main.py "
           "[-r --receiver] <receiver_mail> "
           "[-t --type] <message_type>"
           "[-o --optional-arg] <optional_argument>"))


def parse_args() -> tuple:
    receiver_mail = None
    type_of_mail = None
    optional_argument = None
    options, arguments = getopt(argv[1:], "r:t:o:")
    for option, argument in options:
        if option in ("-r", "--receiver"):
            receiver_mail = argument
        elif option in ("-t", "--type"):
            type_of_mail = argument
        elif option in ("-o", "--optional-arg"):
            optional_argument = argument
    return receiver_mail, type_of_mail, optional_argument


def main() -> None:
    receiver_mail, type_of_mail, optional_argument = parse_args()
    subject, content = load_mail_template(receiver_mail, type_of_mail,
              optional_argument)
    print(subject, "\n", content)
    # send_mail(receiver_mail, type_of_mail, optional_argument)


if __name__ == '__main__':
    main()

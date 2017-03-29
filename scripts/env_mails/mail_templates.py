"""  This module contains mail templates. The templates are as follow:
- 1, Password Lost
- 2, Welcome Mail
- 3, Goodbye Mail
"""

def load_mail_template(receiver_mail: str, type_of_mail: str,
                       optional_argument: str) -> tuple:
    subject = None
    content = None
    if type_of_mail == '0':
        subject, content = _load_test_template()
    elif type_of_mail == '1':
        subject, content = _load_password_lost_template(optional_argument)
    elif type_of_mail == '2':
        subject, content = _load_welcome_template(optional_argument)
    elif type_of_mail == '3':
        subject, content = _load_goodbye_template(optional_argument)
    return subject, content


def _load_test_template() -> tuple:
    subject = "This is a test mail"
    content = ("You shouldn't see this mail in your mailbox.\n"
               "If that is the case, please quickly contact our admins at "
               "twister.3i017@gmail.com\n"
               "Thank you for your effort.\n\n"
               "\tThe Twister team")
    return subject, content


def _load_password_lost_template(hashed_password: str) -> tuple:
    subject = "Your password is lost ?"
    content = ("Hello \n"
               "Here's your new password, keep-it somewhere safe :\n"
               "" + hashed_password + "\n\n"
               "\tThe Twister team")
    return subject, content


def _load_welcome_template(user_name: str) -> tuple:
    subject = "Welcome to Twister !"
    content = ("Hello " + user_name + " and welcome to Twister !\n\n"
               "\tThe Twister team")
    return subject, content


def _load_goodbye_template(user_name: str) -> tuple:
    subject = "Goodbye from Twister"
    content = ("Hello " + user_name + "\n"
               "It's with much sadness that we have learned of the "
               "desactivation of your Twister account. We hope it's just "
               "temporary and that we'll see you soon !\n"
               "Sincerely,\n\n"
               "\tThe Twister team")
    return subject, content


if __name__ == '__main__':
    pass

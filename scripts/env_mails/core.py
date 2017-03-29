""" Many thanks to Nael Shiab for his article about sending mails with Python !
http://naelshiab.com/tutoriel-comment-envoyer-un-courriel-avec-python/
"""
import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText


_SENDER_MAIL = "twister.3i017@gmail.com"
_SENDER_PASSWORD = "nz5QcHdRLNHCPpjK"


def _login(sender_mail: str, sender_password: str) -> callable:
    """ Login the user with his password before executing a
    function, then logout-it.
    """
    def real_decorator(function: callable) -> callable:
        def wrapper(*args: tuple, **kwargs: dict) -> object:
            global _server

            _server = smtplib.SMTP('smtp.gmail.com', 587)
            _server.starttls()
            _server.login(sender_mail, sender_password)

            result = function(*args, **kwargs)

            _server.quit()
            return result
        return wrapper
    return real_decorator


@_login(_SENDER_MAIL, _SENDER_PASSWORD)
def send_mail(receiver_mail: str, subject: str, content: str) -> None:
    global _SENDER_MAIL, _server

    msg = MIMEMultipart()
    msg['From'] = _SENDER_MAIL
    msg['To'] = receiver_mail
    msg['Subject'] = subject
    msg.attach(MIMEText(content, 'plain'))
    text = msg.as_string()
    _server.sendmail(_SENDER_MAIL, receiver_mail, text)


if __name__ == '__main__':
    pass

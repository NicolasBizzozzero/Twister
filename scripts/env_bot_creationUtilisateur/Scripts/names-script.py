#!c:\users\nicolas\desktop\env_megainsert\scripts\python.exe
# EASY-INSTALL-ENTRY-SCRIPT: 'names==0.3.0','console_scripts','names'
__requires__ = 'names==0.3.0'
import re
import sys
from pkg_resources import load_entry_point

if __name__ == '__main__':
    sys.argv[0] = re.sub(r'(-script\.pyw?|\.exe)?$', '', sys.argv[0])
    sys.exit(
        load_entry_point('names==0.3.0', 'console_scripts', 'names')()
    )

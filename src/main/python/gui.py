from turtle import color
import PySimpleGUI as sg
import numpy as np
import pandas as pd
import yfinance as yf
import plotly.graph_objs as go

#global variables
currentPrice = 0.0
Open = 0.0
Close = 0.0
dayLow = 0
dayHigh = 0


def calculate(stk):
    #calculate stock info
    stock = stk
    currentPrice = 0
    Close = 0
    Open = 0
    dayLow =0
    dayHigh = 0
    error = False
    try:
        data = yf.download(tickers=stock, period='1d', interval='1m')
        error = False
        track = yf.Ticker(stock)
        info = track.info
    except UnboundLocalError:
        error = True
        print("Invalid Stock. Enter a US-based stock")

    for key in info:
        if(key == 'currentPrice'):
            currentPrice = info[key]
        if(key == 'open'):
            Open = info[key]
        if(key == 'previousClose'):
            Close = info[key]
        if(key == 'dayLow'):
            dayLow = info[key]
        if(key == 'dayHigh'):
            dayHigh = info[key]
        
    Change = currentPrice - Close
    stock_info = [currentPrice, Open, Close, dayLow, dayHigh, Change]
    print("current Price of "+ stock + " and change today: $" + str(currentPrice) + " :$%.2f " % Change)
    print("Open today at: $" + str(Open))
    print("Today's day low / day high is : $" + str(dayLow) + " / $" + str(dayHigh))
    return stock_info

sg.theme('Green')
layout = [  [sg.Text('Stock Quote Lookup', font="Serif 24")],
            [sg.Text('Please enter a stock ticker', font='Serif 16'), sg.InputText()],
            [sg.Button('OK'), sg.Button('Close')],
            [sg.Text(size=(10,2), key="-BIGL-", font="Serif 22"), sg.Text(size=(10,2), key="-BIGR-", font="Serif 16")],
            [sg.Text('Current Price: $' + str(currentPrice), key="-CURPRICE-", font="Serif 14"), sg.Text('Open: $' + str(Open), key="-OPEN-", font="Serif 14"), sg.Text('Closing Price: $' + str(Close), key="-CLOSE-", font="Serif 14")],
            [sg.Text("Today's Low: $" + str(dayLow), key="-LOW-", font="Serif 14"), sg.Text("Today's High: $" + str(dayHigh), key="-HIGH-", font="Serif 14")]]

# Create the Window
window = sg.Window('Window Title', layout, margins=(150, 75))
# Event Loop to process "events" and get the "values" of the inputs
while True:
    event, values = window.read()
    if event == sg.WIN_CLOSED or event == 'Close': # if user closes window or clicks close
        break
    #print('You entered ', values[0])
    infor = calculate(values[0])
    window["-CURPRICE-"].update('Current Price: $' + str(infor[0]))
    window["-OPEN-"].update('Open: $' + str(infor[1]))
    window["-CLOSE-"].update('Closing Price: $' + str(infor[2]))
    window["-LOW-"].update("Today's Low: $" + str(infor[3]))
    window["-HIGH-"].update("Today's High: $" + str(infor[4]))
    window["-BIGL-"].update("$"+ str(infor[0]))
    short = "%.2f"%infor[5]
    window["-BIGR-"].update("$" + short)
window.close()





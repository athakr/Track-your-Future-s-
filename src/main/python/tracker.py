
import numpy as np
import pandas as pd
import yfinance as yf
import plotly.graph_objs as go

stock = "TSLA"
data = yf.download(tickers=stock, period='1d', interval='1m')


track = yf.Ticker(stock)
info = track.info
currentPrice = 0.0
Open = 0.0

dayLow = 0
dayHigh = 0




for key in info:
  if(key == 'currentPrice'):
    currentPrice = info[key]
  if(key == 'open'):
    Open = info[key]
  if(key == 'dayLow'):
    dayLow = info[key]
  if(key == 'dayHigh'):
    dayHigh = info[key]
    
Change = currentPrice - Open
print(Change)
print("current Price of "+ stock + " and change today: $" + str(currentPrice) + " :$%.2f " % Change)
print("Open today at: $" + str(Open))
print("Today's day low / day high is : $" + str(dayLow) + " / $" + str(dayHigh))



#figures and adding candles
fig = go.Figure()
fig.add_trace(go.Candlestick(x=data.index,
                open=data['Open'],
                high=data['High'],
                low=data['Low'],
                close=data['Close'], name = 'market data'))
# Add titles
fig.update_layout(
    title= stock + ' live share price evolution',
    yaxis_title='Stock Price (USD per Shares)')

# X-Axes
fig.update_xaxes(
    rangeslider_visible=True,
    rangeselector=dict(
        buttons=list([
            dict(count=15, label="15m", step="minute", stepmode="backward"),
            dict(count=45, label="45m", step="minute", stepmode="backward"),
            dict(count=1, label="HTD", step="hour", stepmode="todate"),
            dict(count=3, label="3h", step="hour", stepmode="backward"),
            dict(step="all")
        ])
    )
)

#Show
fig.show()
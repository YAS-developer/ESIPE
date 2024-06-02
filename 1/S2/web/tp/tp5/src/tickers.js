"use strict";

window.onload = () => {
  let refreshButton = document.getElementById("refreshButton");
  let stockTable = document.getElementById("stockTable");
  let markText = document.getElementById("markText");

  async function fetchTickers() {
    const response = await fetch('/api/ticker');
    return response.json();
  }

  async function fetchStocks() {
    const response = await fetch('/api/stock', { method: 'POST' });
    return response.json();
  }

  function highlightStocks(threshold) {
    const rows = document.querySelectorAll('#stockTable tr');
    rows.forEach(row => {
      for (let i = 1; i <= 10; i++) {
        const cell = row.children[i];
        const value = parseInt(cell.textContent, 10);
        if (!isNaN(value) && value < threshold) {
          cell.style.color = 'green';
        } else {
          cell.style.color = 'black';
        }
      }
    });
  }

  let refreshTimeout;
  let stockHistory = {};

  async function refreshData() {
    clearTimeout(refreshTimeout); // Clear previous timeout to avoid multiple triggers
    try {
      const tickers = await fetchTickers();
      const stocks = await fetchStocks();

      tickers.forEach(ticker => {
        if (!stockHistory[ticker]) {
          stockHistory[ticker] = [];
        }
        stockHistory[ticker].unshift(stocks[ticker]);
        if (stockHistory[ticker].length > 10) {
          stockHistory[ticker].pop();
        }
      });

      stockTable.innerHTML = ''; // Clear the table before refreshing
      const headerRow = document.createElement('tr');
      headerRow.innerHTML = '<th>Ticker</th><th>Q1</th><th>Q2</th><th>Q3</th><th>Q4</th><th>Q5</th><th>Q6</th><th>Q7</th><th>Q8</th><th>Q9</th><th>Q10</th>';
      stockTable.appendChild(headerRow);

      tickers.forEach(ticker => {
        const row = document.createElement('tr');
        const tickerCell = document.createElement('td');
        tickerCell.textContent = ticker;
        row.appendChild(tickerCell);

        for (let i = 0; i < 10; i++) {
          const stockCell = document.createElement('td');
          stockCell.textContent = stockHistory[ticker][i] !== undefined ? stockHistory[ticker][i] : '-';
          row.appendChild(stockCell);
        }
        stockTable.appendChild(row);
      });

      const threshold = parseInt(markText.value, 10);
      if (!isNaN(threshold)) {
        highlightStocks(threshold);
      }

      refreshTimeout = setTimeout(refreshData, 1000); // Refresh every second
    } catch (error) {
      console.error('Error:', error);
    }
  }

  refreshButton.onclick = () => {
    stockHistory = {};
    refreshData();
  };

  markText.onkeyup = function() {
    const threshold = parseInt(this.value, 10);
    if (!isNaN(threshold)) {
      highlightStocks(threshold);
    }
  };
};

# Connect Four and Tic Tac Toe Game
## Overview
This project implements two classic board games, Connect Four and Tic Tac Toe, with a graphical user interface (GUI) using Java's Swing library. It supports two gameplay modes:

1. Player vs Player – Two players can play the game on the same device.
2. Player vs AI – A human player competes against an AI, with adjustable difficulty.

The project uses the Minimax algorithm with Alpha-Beta pruning to enable the AI to make intelligent moves in Connect Four and Tic Tac Toe. It also provides a clean and interactive user experience with score tracking and game resets.

## File Structure
<ul>
<li>Board.java: An abstract class that provides a blueprint for both Tic Tac Toe and Connect Four boards.</li>
<li>ConnectFourBoard.java: Implementation of the Board class for the Connect Four game.</li>
<li>TicTacToeBoard.java: Implementation of the Board class for the Tic Tac Toe game.</li>
<li>ConnectFourGame.java: Contains the logic for playing Connect Four, including GUI components and AI interactions.</li>
<li>GameMenu.java: A simple menu that lets the user choose between Tic Tac Toe and Connect Four.</li>
<li>TicTacToeGame.java: Contains the logic for playing Tic Tac Toe, including GUI components.</li>
</ul>

## Game Rules
Connect Four
<ul>
<li>Objective: Connect four of your pieces in a row, either horizontally, vertically, or diagonally.</li>
<li>Board: The game is played on a 6x7 grid.</li>
<li>Gameplay: Players take turns dropping pieces into columns. The first player to connect four pieces wins.</li>
</ul>
Tic Tac Toe
<ul>
<li>Objective: Get three of your pieces in a row, either horizontally, vertically, or diagonally.</li>
<li>Board: The game is played on a 3x3 grid.</li>
<li>Gameplay: Players alternate placing "X" or "O" on the board. The first to align three pieces wins.</li>
</ul>

## Features
<ul>
<li>Player vs Player: Two players can play against each other on the same computer.</li>
<li>Player vs AI: Play against the AI, with adjustable difficulty for the Connect Four game (difficulty levels 1–10).</li>
<li>Minimax Algorithm: The AI uses the Minimax algorithm with Alpha-Beta pruning to make optimal moves.</li>
<li>Score Tracking: Displays the number of wins for each player and the number of draws.</li>
<li>Game Reset: Players can reset the game at any time and start a new match.</li>
<li>Responsive UI: A clean interface with buttons for interacting with the game and returning to the main menu.</li>
</ul>

## How to Play
### Tic Tac Toe:
<ol>
<li>Choose Tic Tac Toe from the main menu.</li>
<li>Click on any empty space to place your "X" or "O" (depending on the player).</li>
<li>The first player to align three marks in a row wins.</li>
<li>The game will announce the winner or if it's a draw.</li>
<li>You can reset the game at any time.</li>
</ol>

### Connect Four:
<ol>
<li>Choose Connect Four from the main menu.</li>
<li>Choose between Player vs Player or Player vs AI.</li>
<li>If you choose Player vs AI, the AI will make a move after the human player.</li>
<li>The first player to connect four marks in a row wins.</li>
<li>You can reset the game at any time.</li>
</ol>

### AI Difficulty:
<ul>
<li>In Player vs AI, after selecting the mode, you will be prompted to enter a difficulty level for the AI (1 to 10).</li>
<li>Higher numbers mean a more challenging AI opponent.</li>
</ul>

### Game Controls
<ul>
<li>Mouse: Click on the buttons to make a move.</li>
<li>Back to Main Menu: After finishing a game, you can return to the main menu by clicking the "Back to Main Menu" button.</li>
</ul>

### AI Algorithm
The AI for Connect Four uses the Minimax algorithm with Alpha-Beta pruning to optimize its decision-making. The algorithm simulates potential moves and selects the one that maximizes its chances of winning while minimizing the opponent's.

## Score Tracking
<ul>
<li>X Wins: Number of wins by player "X".</li>
<li>O Wins: Number of wins by player "O".</li>
<li>Draws: Number of games that ended in a draw.</li>
</ul>
The score is displayed at the top of the screen and updates after each round.

# Simplified Advance Wars Computer Opponent (SACO)

## Project Goal: Create a simplified java version of the classic Advance Wars game and create a computer opponent for it.

## Contents
* Part 1: Simplified Advance wars game play
* Part 2: Computer Opponent
* Part 3: SACO Code Documentation

## Part 1: Simplified Advance wars game play

Advance Wars information: http://advancewars.wikia.com/wiki/Advance_Wars_(game)
  
### Features of SACO:
- Same grid based, turn based, unit movement game
- Both HQ capture and unit destruction win conditions
- Varied terrain with defense values
- Income per turn
- Healing of units
- Creation of units at factories
- Custom map loading for CSV files
- Damage formula from AWBW: http://awbw.wikia.com/wiki/Damage_Formula
  
### Simplifications in SACO:
- Limited units: infantry
- Limited terrain: mountains, woods, plains, roads, sea
- Limited buildings: HQ, city, base
- Two Countries: Orange Star, Blue Moon
- No COs
- No weather

## Part 2: Computer Opponent

## Part 3: SACO Code Documentation

### MVC Architecture

### Design Patterns Used:
- Singleton Pattern for resource files
- Factory Pattern to create units
- Observer and State Pattern as part of slick 2d
- Strategy Pattern (to be implemented) for AI

### What I like about the project:
- The modular structure of the code allows easy additions.

### What I learned:
- How to use slick 2d to some degree (not the most straightforward)
- How to implement design patterns within an architecture
- Using code writing tools: SonarQube/CheckStyle

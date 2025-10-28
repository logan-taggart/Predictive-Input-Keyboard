# Predictive Input Keyboard

A Java project exploring predictive text and autocomplete functionality using **Trie** and **Hashtable** data structures. Created as part of a directed study project under the guidance of Professor Tony Tian at Eastern Washington University.

## Overview
The application allows users to type directly into a GUI and see word predictions appear dynamically as they type. It includes two interchangeable backend implementations:
- **Trie based approach** – optimized for fast prefix lookups.  
- **Hashtable based approach** – simpler structure with efficient insertions and lookups.

## Features
- Interactive **Java GUI** for real-time word prediction  
- Two interchangeable data structure implementations (Trie and Hashtable)  
- Fast (Real-Time) and accurate autocomplete suggestions  
- Utility classes for dictionary loading, prefix trie creation and performance testing

## Compile & Run

### 1. Clone the repository
```bash
git clone https://github.com/logan-taggart/Predictive-Input-Keyboard.git
cd Predictive-Input-Keyboard
```

### 2. Pick the implementation to compile
```bash
cd "Trie Keyboard Implementation"
javac AutoCompleteStudent.java
```

### 3. Run the program
```bash
java AutoCompleteStudent
```

## Project Structure
```
Predictive-Input-Keyboard/
│
├── TrieKeyboardImplementation/        # Trie-based predictive model
├── HashtableKeyboardImplementation/   # Hashtable-based predictive model
└── Utilities/                         # Shared utility classes
```

## Future Improvements
- Implement adaptive learning to personalize suggestions based on typing history through ML techniques
- Update GUI design with modern styling
- Support phrase prediction (multi-word suggestions instead of single words) 

## Authors
##### Developed by: Logan Taggart & Tony Tian
##### CSCD 499 Directed Study Project – Eastern Washington University

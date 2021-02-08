import random

def playgame():
    try:
        play = True
        bestscore = 10
        while(play):
            tries = 0
            randNum = random.randint(1, 10)
            win = False
            guesses = []
            print("Guess a number between 1 and 10: ")

            while (not win):
                try:
                    Guess = int(input())
                except:
                    print("That's not a number between one and ten. Guess again:")
                    continue

                guessagain = False
                if (Guess < 11 and Guess > 0):
                    if Guess in guesses:
                        print("You have already guessed this number. Guess again:")
                        continue
                    tries += 1

                    if (Guess == randNum):
                        win = True
                    elif (Guess < randNum):
                        print("Guess higher..")
                    elif (Guess > randNum):
                        print("Guess lower..")
                
                else:
                    print("That's not a number between one and ten.. Guess again: ")
                guesses.append(Guess)
        
            print("You guessed it!")
            print("Number of tries: ", tries)

            if (tries < bestscore):
                bestscore = tries
        
            while True:
                print("Do you want to play again?")
                play_input = str(input())
                if ("n" in play_input):
                    play = False
                    break
                elif ("y" in play_input or "s" in play_input):
                    play = True
                    break
                else:
                    print("I don't know what you mean..")

        return bestscore
    finally:
        print("Error occurred")

print("Your best score is:", playgame())
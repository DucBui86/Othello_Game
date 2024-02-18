# Violet-22



## Getting started

To make it easy for you to get started with GitLab, here's a list of recommended next steps.

Already a pro? Just edit this README.md and make it your own. Want to make it easy? [Use the template at the bottom](#editing-this-readme)!

## Add your files

- [ ] [Create](https://docs.gitlab.com/ee/user/project/repository/web_editor.html#create-a-file) or [upload](https://docs.gitlab.com/ee/user/project/repository/web_editor.html#upload-a-file) files
- [ ] [Add files using the command line](https://docs.gitlab.com/ee/gitlab-basics/add-file.html#add-a-file-using-the-command-line) or push an existing Git repository with the following command:

```
cd existing_repo
git remote add origin https://gitlab.utwente.nl/software-systems/2022-2023/student-projects/violet/violet-22.git
git branch -M main
git push -uf origin main
```

## Integrate with your tools

- [ ] [Set up project integrations](https://gitlab.utwente.nl/software-systems/2022-2023/student-projects/violet/violet-22/-/settings/integrations)

## Collaborate with your team

- [ ] [Invite team members and collaborators](https://docs.gitlab.com/ee/user/project/members/)
- [ ] [Create a new merge request](https://docs.gitlab.com/ee/user/project/merge_requests/creating_merge_requests.html)
- [ ] [Automatically close issues from merge requests](https://docs.gitlab.com/ee/user/project/issues/managing_issues.html#closing-issues-automatically)
- [ ] [Enable merge request approvals](https://docs.gitlab.com/ee/user/project/merge_requests/approvals/)
- [ ] [Automatically merge when pipeline succeeds](https://docs.gitlab.com/ee/user/project/merge_requests/merge_when_pipeline_succeeds.html)

## Test and Deploy

Use the built-in continuous integration in GitLab.

- [ ] [Get started with GitLab CI/CD](https://docs.gitlab.com/ee/ci/quick_start/index.html)
- [ ] [Analyze your code for known vulnerabilities with Static Application Security Testing(SAST)](https://docs.gitlab.com/ee/user/application_security/sast/)
- [ ] [Deploy to Kubernetes, Amazon EC2, or Amazon ECS using Auto Deploy](https://docs.gitlab.com/ee/topics/autodevops/requirements.html)
- [ ] [Use pull-based deployments for improved Kubernetes management](https://docs.gitlab.com/ee/user/clusters/agent/)
- [ ] [Set up protected environments](https://docs.gitlab.com/ee/ci/environments/protected_environments.html)

***

# Othello Game

Othello is a two-player board game where the two players take turn placing discs of their color. Each move must capture at least one disc of the opponent. If this is not possible, the player must pass, unless the other player also cannot make such a move. The game ends when neither player can capture from their opponent. The winner is the player with the most discs. The game ends in a draw when both players have the same number of dics.
Othello is a variant of the game Reversi.
## Requirements
The project requires java to set up 

## Name
Bui Duc Cuong 
s-29966174

## Description

Project Module 2: Othello game.

## Badges
User interface when users connect to server to play game:
![img_2.png](img_2.png)

Image show the player in the Othello game.
![img_3.png](img_3.png)

## Visuals
When the player get hint and do move in the Othello game.
![img_4.png](img_4.png)
## Installation
The first time to play the Othello game, users should know about the rules of Othello game.

The rules:
Black plays first. Each turn consists of a single valid move, which either places a disc on the
board, or is a passing move. The current player must place a disc of their color on an unoccupied
square of the game board, such that at least one disc of the opponent is captured. If this is not
possible and the opponent could play such a move, then the player must pass. A player may
only pass if they cannot play a normal move and the opponent can play a normal move.
Discs are captured when they are outflanked by the newly placed disc and an existing disc
of the same color. See Figure 1 for an example. Here a black disc is played on E6. The white
disc on D5 is captured. The white disc on E5 is not captured. If there was a white disc on E4,
then all three white discs on E3, E4, and E5 would be captured. Discs are captured along the
horizontal, vertical and diagonal directions. Discs on the corners cannot be captured.
The game ends when neither player can place a disc that captures discs of the opponent.
The winner of Othello is the player with most discs. The game ends in a draw when both
players have the same number of discs.

Besides, when player play the game, it has hints to support the player.

## Usage

### Start Client
Below are the instructions for connecting server, and using client user interface:
> Go to src file -> open client folder -> run ClientTUI.java.
>
> Enter port number (44444) -> Enter IP address (130.89.253.64) -> Enter your choice in the console.
>
> Using "hint" to get help in the Othello game by pressing "88".
### Start Othello game (offline)
Below are the instructions for playing offline Othello game.
> Go to src file -> open ui folder -> run OthelloTUI.java
> 
> Enter username (player name). If you want to use SmartStrategy, type "-S" or NaiveStrategy, type"-N".

## Support

If you have any problems with the client and game logic, contact me!!!

Email: buiduccuong@student.utwente.nl

## Authors and acknowledgment
Bui Duc Cuong

Thank you for everything!!!


# Guideline for contributions

This repository and its GitHub Project, hereafter mentioned as *the scrum board*, are tied exclusively to each other. Thank you for collaborating.

## Creating issues

Issues involving the repository are placed in the scrum board's **Product backlog** and are made as either a new user story, a new task linked to an existing user story, or a bugfix. They must be labeled as such. There can be multiple bugfixes proposed in a bugfix issue. User story issues have a title called **US#** with the next available number and a title of very few words, and a description containing the formulated user story and its acceptance criteria. It can contain tasks.

## Flow of issues

The collaborator team make a proposed **Sprint backlog** together by moving issues there at a meeting. When the **Sprint backlog** is approved by the product owner, issues can be moved to **In progress** by the collaborator making a feature branch for that issue. The collaborator thereby commits to solving that issue and making all its relevant tests. When the issue is resolved and tested, the collaborator merges the **main** branch into the feature branch, makes a pull-request, makes sure the CI doesn't fail, and the issue is moved to **In review**. Another collaborator reviews it, merges the feature it into main, and the issue is moved to **Done**. When **main** is pulled into the **deploy** branch, all issues in **Done** are moved to **Deployed**.
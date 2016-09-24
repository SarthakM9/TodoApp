# Pre-work - TodoApp

TodoApp is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: Sarthak Mittal

Time spent: 16 hours spent in total

## User Stories

The following functionality is completed:

* [x] User can successfully add and remove items from the todo list
* [x] User can tap a todo item in the list and bring up an edit screen for the todo item and then have any changes to the text reflected in the todo list.
* [x] User can persist todo items and retrieve them properly on app restart

The following optional features are implemented:

* [x] Persist the todo items into SQLite instead of a text file (content provider used along with AsyncQueryHandler).
* [x] Improve style of the todo items in the list (custom recycylerview adapter implemented with cursor support).
* [x] Add support for completion due dates for todo items and display within list item.
* [x] Use a Fragment instead of new Activity for editing items.
* [x] Add support for selecting the priority of each todo item and display in list item.
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

## Notes:

Spent some time on implementing a content provider to access SQLite database.

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='https://github.com/mrdevilbynature/TodoApp/blob/master/todoappvid.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with LiceCap(http://www.cockos.com/licecap/).

## License

    Copyright 2016 Sarthak Mittal

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

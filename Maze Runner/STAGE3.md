<h2 style="text-align: center;">Description</h2>

<p>The program should provide a menu with five options:</p>

<ol>
	<li>Generate a new maze.</li>
	<li>Load a maze.</li>
	<li>Save the maze.</li>
	<li>Display the maze.</li>
	<li>Exit.</li>
</ol>

<p>After a maze is generated or loaded from a file, it becomes the current maze that can be saved or displayed.</p>

<p>If there is no current maze (generated or loaded), a user should not see the options <strong>save </strong>and <strong>display the maze</strong>. If a user chooses an option that requires a file, he must enter a path to the file. You must always check the result of processing files and display user-friendly messages.</p>

<p>The program should output the maze to the user only in two scenarios:</p>

<ul>
	<li>After <strong>generating </strong>a maze;</li>
	<li>After choosing an option <strong>display the maze</strong>.</li>
</ul>

<p>Your program must successfully handle the following cases:</p>

<ul>
	<li>if an incorrect option was chosen, the program must print a message like <strong>"Incorrect option. Please try again";</strong></li>
	<li>if a file to load a maze does not exist, the program should not stop, it must print a message like <strong>"The file ... does not exist"</strong>;</li>
	<li>if a file has an invalid format for a maze, the program should not stop, but it must print a message like <strong>"Cannot load the maze. It has an invalid format"</strong>.</li>
</ul>

<p>By the way, you can store the maze in any format, the tests do not check the contents of the file. The most important thing is that the saved maze must be correctly loaded into the program.</p>

<h2 style="text-align: center;">Example</h2>

<p>After starting, your program must print a menu listing only appropriate options. When a user has chosen an option, the program must perform the corresponding action. Notice, that maze should be a square.</p>

<pre><code class="language-no-highlight">=== Menu ===
1. Generate a new maze
2. Load a maze
0. Exit
&gt;1
Enter the size of a new maze
&gt;17
██████  ██████████████████████████
██████  ██████████████████████████
██  ██                          ██
██  ██████████████  ██████████████
██  ██              ██  ██  ██  ██
██  ██████████████  ██  ██  ██  ██
██      ██                      ██
██████  ██████████  ██  ██████████
██      ██          ██  ██      ██
██████  ██████████  ██████  ██████
██              ██              ██
██  ██  ██  ██  ██  ██████████████
██  ██  ██  ██              ██  ██
██  ██████████  ██  ██  ██████  ██
██  ██          ██  ██          ██
████████  ████████████████████████
████████  ████████████████████████

=== Menu ===
1. Generate a new maze
2. Load a maze
3. Save the maze
4. Display the maze
0. Exit
&gt;3
&gt;maze.txt

=== Menu ===
1. Generate a new maze
2. Load a maze
3. Save the maze
4. Display the maze
0. Exit
&gt;0
Bye!</code></pre>

<p><strong>Note, </strong>the program should not stop until the user selects the exit option.</p>
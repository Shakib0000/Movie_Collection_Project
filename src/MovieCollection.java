
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MovieCollection
{
  private ArrayList<Movie> movies;
  private Scanner scanner;

  public MovieCollection(String fileName)
  {
    importMovieList(fileName);
    scanner = new Scanner(System.in);
  }

  public ArrayList<Movie> getMovies()
  {
    return movies;
  }
  
  public void menu()
  {
    String menuOption = "";
    
    System.out.println("Welcome to the movie collection!");
    System.out.println("Total: " + movies.size() + " movies");
    
    while (!menuOption.equals("q"))
    {
      System.out.println("------------ Main Menu ----------");
      System.out.println("- search (t)itles");
      System.out.println("- search (k)eywords");
      System.out.println("- search (c)ast");
      System.out.println("- see all movies of a (g)enre");
      System.out.println("- list top 50 (r)ated movies");
      System.out.println("- list top 50 (h)igest revenue movies");
      System.out.println("- (q)uit");
      System.out.print("Enter choice: ");
      menuOption = scanner.nextLine();
      
      if (!menuOption.equals("q"))
      {
        processOption(menuOption);
      }
    }
  }
  
  private void processOption(String option)
  {
    if (option.equals("t"))
    {
      searchTitles();
    }
    else if (option.equals("c"))
    {
      searchCast();
    }
    else if (option.equals("k"))
    {
      searchKeywords();
    }
    else if (option.equals("g"))
    {
      listGenres();
    }
    else if (option.equals("r"))
    {
      listHighestRated();
    }
    else if (option.equals("h"))
    {
      listHighestRevenue();
    }
    else
    {
      System.out.println("Invalid choice!");
    }
  }
  
  private void searchTitles()
  {
    System.out.print("Enter a title search term: ");
    String searchTerm = scanner.nextLine();
    
    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();
    
    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();
    
    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieTitle = movies.get(i).getTitle();
      movieTitle = movieTitle.toLowerCase();
      
      if (movieTitle.indexOf(searchTerm) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }
    
    // sort the results by title
    sortResults(results);
    
    // now, display them all to the user    
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();
      
      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;
      
      System.out.println("" + choiceNum + ". " + title);
    }
    
    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");
    
    int choice = scanner.nextInt();
    scanner.nextLine();
    
    Movie selectedMovie = results.get(choice - 1);
    
    displayMovieInfo(selectedMovie);
    
    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void sortResults(ArrayList<Movie> listToSort)
  {
    for (int j = 1; j < listToSort.size(); j++)
    {
      Movie temp = listToSort.get(j);
      String tempTitle = temp.getTitle();
      
      int possibleIndex = j;
      while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
      {
        listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
        possibleIndex--;
      }
      listToSort.set(possibleIndex, temp);
    }
  }
  
  private void displayMovieInfo(Movie movie)
  {
    System.out.println();
    System.out.println("Title: " + movie.getTitle());
    System.out.println("Tagline: " + movie.getTagline());
    System.out.println("Runtime: " + movie.getRuntime() + " minutes");
    System.out.println("Year: " + movie.getYear());
    System.out.println("Directed by: " + movie.getDirector());
    System.out.println("Cast: " + movie.getCast());
    System.out.println("Overview: " + movie.getOverview());
    System.out.println("User rating: " + movie.getUserRating());
    System.out.println("Box office revenue: " + movie.getRevenue());
  }
  
  private void searchCast()
  {
    System.out.print("Enter an actor: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    ArrayList<String> results = new ArrayList<String>();
    ArrayList<String> matchingResults = new ArrayList<String>();

    for (int i = 0; i < movies.size(); i++)
    {
      String movieActors[] = movies.get(i).getCast().split("\\|");
      for (int j = 0; j < movieActors.length; j++) {
        if (movieActors[j].toLowerCase().indexOf(searchTerm) != -1) {
          boolean found = false;
          for (int k = 0; k < matchingResults.size(); k++) {
            if (matchingResults.get(k).equals(movieActors[j])) {
              found = true;
            }
          }
          if (!found) {
            matchingResults.add(movieActors[j]);
          }
        }
      }
    }

    for (int j = 1; j < matchingResults.size(); j++)
    {
      String temp = matchingResults.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && temp.compareTo(matchingResults.get(possibleIndex - 1)) < 0)
      {
        matchingResults.set(possibleIndex, matchingResults.get(possibleIndex - 1));
        possibleIndex--;
      }
      matchingResults.set(possibleIndex, temp);
    }

    // now, display them all to the user
    for (int i = 0; i < matchingResults.size(); i++)
    {
      String actor = matchingResults.get(i);

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + actor);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    String selectedActor = matchingResults.get(choice - 1);
    selectedActor = selectedActor.toLowerCase();

    ArrayList<Movie> movieResults = new ArrayList<Movie>();

    for (int i = 0; i < movies.size(); i++)
    {
      String movieActors[] = movies.get(i).getCast().split("\\|");
      boolean isInMovie = false;
      for (int j = 0; j < movieActors.length; j++) {
        if (movieActors[j].toLowerCase().equals(selectedActor)) {
          isInMovie = true;
        }
      }
      if (isInMovie) {
        movieResults.add(movies.get(i));
      }
    }

    sortResults(movieResults);

    // now, display them all to the user
    for (int i = 0; i < movieResults.size(); i++)
    {
      String title = movieResults.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = movieResults.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }

  private void searchKeywords()
  {
    System.out.print("Enter a keyword search term: ");
    String searchTerm = scanner.nextLine();

    // prevent case sensitivity
    searchTerm = searchTerm.toLowerCase();

    // arraylist to hold search results
    ArrayList<Movie> results = new ArrayList<Movie>();

    // search through ALL movies in collection
    for (int i = 0; i < movies.size(); i++)
    {
      String movieKeywords = movies.get(i).getKeywords();
      movieKeywords = movieKeywords.toLowerCase();

      if (movieKeywords.indexOf(searchTerm) != -1)
      {
        //add the Movie objest to the results list
        results.add(movies.get(i));
      }
    }

    // sort the results by title
    sortResults(results);

    // now, display them all to the user
    for (int i = 0; i < results.size(); i++)
    {
      String title = results.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = results.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void listGenres()
  {

    ArrayList<String> genreList = new ArrayList<String>();

    for (int i = 0; i < movies.size(); i++)
    {
      String movieGenres[] = movies.get(i).getGenres().split("\\|");
      for (int j = 0; j < movieGenres.length; j++) {
          boolean found = false;
          for (int k = 0; k < genreList.size(); k++) {
            if (movieGenres[j].equals(genreList.get(k))) {
              found = true;
            }
          }
          if (!found) {
            genreList.add(movieGenres[j]);
          }
      }
    }

    for (int j = 1; j < genreList.size(); j++)
    {
      String temp = genreList.get(j);

      int possibleIndex = j;
      while (possibleIndex > 0 && temp.compareTo(genreList.get(possibleIndex - 1)) < 0)
      {
        genreList.set(possibleIndex, genreList.get(possibleIndex - 1));
        possibleIndex--;
      }
      genreList.set(possibleIndex, temp);
    }

    // now, display them all to the user
    for (int i = 0; i < genreList.size(); i++)
    {
      String genre = genreList.get(i);

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + genre);
    }

    System.out.println("Which genre would you like to pick?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    String selectedGenre = genreList.get(choice - 1);
    selectedGenre = selectedGenre.toLowerCase();

    ArrayList<Movie> movieResults = new ArrayList<Movie>();

    for (int i = 0; i < movies.size(); i++)
    {
      String movieGenres[] = movies.get(i).getGenres().split("\\|");
      boolean isAGenre = false;
      for (int j = 0; j < movieGenres.length; j++) {
        if (movieGenres[j].toLowerCase().equals(selectedGenre)) {
          isAGenre = true;
        }
      }
      if (isAGenre) {
        movieResults.add(movies.get(i));
      }
    }

    sortResults(movieResults);

    // now, display them all to the user
    for (int i = 0; i < movieResults.size(); i++)
    {
      String title = movieResults.get(i).getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title);
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = movieResults.get(choice - 1);

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void listHighestRated()
  {
    double highest = 0;
    Movie[] top50Rated = new Movie[50];
    String[] placedMovieTitles = new String[50];
    for (int i = 0; i < placedMovieTitles.length; i++) {
      placedMovieTitles[i] = "";
    }

    for (int i = 0; i < top50Rated.length; i++) {
      int placedMovieIndex = 0;
      highest = 0;
      int highestIndex = 0;
      for (int j = 0; j < movies.size(); j++) {
        if (movies.get(j).getUserRating() >= highest) {
          boolean found = false;
          for (int k = 0; k < placedMovieTitles.length; k++) {
            if (placedMovieTitles[k].equals(movies.get(j).getTitle())) {
              found = true;
              break;
            }
          }
          if (!found) {
            for (int l = 0; l < placedMovieTitles.length; l++) {
              if (placedMovieTitles[l].equals("")) {
                placedMovieIndex = l;
                break;
              }
            }
            highest = movies.get(j).getUserRating();
            highestIndex = j;
          }
        }
      }
      top50Rated[i] = movies.get(highestIndex);
      placedMovieTitles[placedMovieIndex] = movies.get(highestIndex).getTitle();
    }

    for (int i = 0; i < top50Rated.length; i++)
    {
      String title = top50Rated[i].getTitle();

      // this will print index 0 as choice 1 in the results list; better for user!
      int choiceNum = i + 1;

      System.out.println("" + choiceNum + ". " + title + ": " + top50Rated[i].getUserRating());
    }

    System.out.println("Which movie would you like to learn more about?");
    System.out.print("Enter number: ");

    int choice = scanner.nextInt();
    scanner.nextLine();

    Movie selectedMovie = top50Rated[choice - 1];

    displayMovieInfo(selectedMovie);

    System.out.println("\n ** Press Enter to Return to Main Menu **");
    scanner.nextLine();
  }
  
  private void listHighestRevenue()
  {
  
  }
  
  private void importMovieList(String fileName)
  {
    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line = bufferedReader.readLine();
      
      movies = new ArrayList<Movie>();
      
      while ((line = bufferedReader.readLine()) != null) 
      {
        String[] movieFromCSV = line.split(",");
     
        String title = movieFromCSV[0];
        String cast = movieFromCSV[1];
        String director = movieFromCSV[2];
        String tagline = movieFromCSV[3];
        String keywords = movieFromCSV[4];
        String overview = movieFromCSV[5];
        int runtime = Integer.parseInt(movieFromCSV[6]);
        String genres = movieFromCSV[7];
        double userRating = Double.parseDouble(movieFromCSV[8]);
        int year = Integer.parseInt(movieFromCSV[9]);
        int revenue = Integer.parseInt(movieFromCSV[10]);
        
        Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
        movies.add(nextMovie);  
      }
      bufferedReader.close();
    }
    catch(IOException exception)
    {
      // Print out the exception that occurred
      System.out.println("Unable to access " + exception.getMessage());              
    }
  }
}
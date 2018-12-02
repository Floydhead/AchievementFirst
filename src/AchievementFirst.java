import java.util.Scanner;

public class AchievementFirst {
    public static void main(String args[]) {
        CallGithubAPI callGithubAPI = new CallGithubAPI();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Input Github Username [eg: Floydhead]");
        String username = scanner.nextLine();
        System.out.println("Input Type of Call: All, Owner, Member");
        String type = scanner.nextLine().toLowerCase();
        if (type.equals("all") || type.equals("member") || type.equals("owner")) {
            String githubResponse = callGithubAPI.getResponse(username, type);
            callGithubAPI.display(callGithubAPI.getJSONResponse(githubResponse));
        } else {
            System.out.println("Please input valid type values");
        }
    }
}

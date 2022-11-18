package uz.enterprise.mytex.common.bot;

import lombok.RequiredArgsConstructor;
import org.kohsuke.github.GHEvent;
import org.kohsuke.github.GHEventInfo;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPerson;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.enterprise.mytex.service.PropertyService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MonitoringBot extends TelegramLongPollingBot {
    private final PropertyService propertyService;
    private final GitHub gitHub;


    @Override
    public void onUpdateReceived(Update update) {
        /*
           left empty because monitoring bot does not
           have any business functionality
         */
    }

    @Scheduled(cron = "0 0 0/3 * * ?")
    public void listenToGithubPullRequests() throws Exception {
        var repositoryName = "mytexuz/mytex";
        GHRepository repository = gitHub.getRepository(repositoryName);
        if (Objects.nonNull(repository)){
            List<GHPullRequest> pullRequests = repository.getPullRequests(GHIssueState.OPEN);
            for (GHPullRequest pr : pullRequests) {
                var prUrl = pr.getHtmlUrl();
                String assignee = pr.getUser().getLogin();
                String reviewers = pr.getRequestedReviewers()
                        .stream()
                        .map(GHPerson::getLogin)
                        .collect(Collectors.joining(","));
                String title = pr.getTitle();
                int numberOfCommits = pr.getCommits();
                int additions = pr.getAdditions();
                int deletions = pr.getDeletions();
                int changedFiles = pr.getChangedFiles();

                SendMessage message = new SendMessage();
                message.enableHtml(true);
                message.setChatId(propertyService.getChatId());

                String messageContent = String.format("""
                            <b> Open Pull Requests ⚠️ </b>
                            <b> Title: </b> %s
                            <b> Assignee: </b> %s
                            <b> Commits: </b> %s
                            <b> Additions: </b> %s
                            <b> Deletions: </b> %s
                            <b> Changed files: </b> %s
                            <b> PR url: </b> %s
                            <b> Reviewers: </b> %s
                            """, title, assignee, numberOfCommits, additions, deletions, changedFiles
                        ,prUrl, reviewers);
                message.setText(messageContent);
                execute(message);
            }
        }
    }
    @Override
    public String getBotUsername() {
        return propertyService.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return propertyService.getBotToken();
    }
}

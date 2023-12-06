## Created for sending emails; used "smtp" of Google.

```Java

GMailSender smtp = GMailSender.authenticate(login,password);
smtp.send(List<String> recipients, String subject, String msg);
```

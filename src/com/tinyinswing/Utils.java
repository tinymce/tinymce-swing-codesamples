package com.tinyinswing;

public class Utils {

  public final static String welcomeText = "<!DOCTYPE html>\n"+
      "<html>\n"+
      "<head>\n"+
      "</head>\n"+
      "<body>\n"+
      "<p style=\"text-align: center;\">&nbsp;</p>\n"+
      "<p style=\"text-align: center;\"><span style=\"color: #bdc3c7;\">&rarr; This is a full-featured editor demo. Please explore! &larr;</span></p>\n"+
      "<p style=\"text-align: center;\">&nbsp;</p>\n"+
      "<h2 style=\"text-align: center; color: #1976d2;\">TinyMCE provides a <span style=\"text-decoration: underline;\">full-featured</span> rich text editing experience, <br />and a featherweight download.</h2>\n"+
      "<p style=\"text-align: center;\">&nbsp;</p>\n"+
      "<p style=\"text-align: center;\"><span style=\"font-size: 14pt;\"> <strong> <span style=\"color: #7e8c8d;\">No matter what you're building, TinyMCE has got you covered.</span> </strong> </span></p>\n"+
      "<table style=\"border-collapse: collapse; width: 70%; margin-left: auto; margin-right: auto; border: 0;\">\n"+
      "<tbody>\n"+
      "<tr>\n"+
      "<td style=\"width: 25%; text-align: center; padding: 7px;\"><span style=\"color: #95a5a6;\">\uD83D\uDEE0 50+ Plugins</span></td>\n"+
      "<td style=\"width: 25%; text-align: center; padding: 7px;\"><span style=\"color: #95a5a6;\">\uD83D\uDCA1 Premium Support</span></td>\n"+
      "<td style=\"width: 25%; text-align: center; padding: 7px;\"><span style=\"color: #95a5a6;\">\uD83D\uDD8D Custom Skins</span></td>\n"+
      "<td style=\"width: 25%; text-align: center; padding: 7px;\"><span style=\"color: #95a5a6;\">⚙ Full API Access</span></td>\n"+
      "</tr>\n"+
      "</tbody>\n"+
      "</table>\n"+
      "<p style=\"text-align: center;\">&nbsp;</p>\n"+
      "</body>\n"+
      "</html>";

  public final static String sampleText = "<p style=\"text-align: center;\"><img title=\"TinyMCE Logo\" src=\"https://www.tiny.cloud/images/glyph-tinymce@2x.png\" alt=\"TinyMCE Logo\" width=\"110\" height=\"97\" /></p>\n" +
      "<h1 style=\"text-align: center;\">Welcome to the TinyMCE editor demo!</h1>\n" +
      "<p>Please try out the features provided in this basic example.<br />Note that any <strong>MoxieManager</strong> file and image management functionality in this example is part of our commercial offering &ndash; the demo is to show the integration.</p>\n" +
      "<h2>Got questions or need help?</h2>\n" +
      "<ul>\n" +
      "<li>Our <a href=\"https://www.tiny.cloud/docs/\">documentation</a> is a great resource for learning how to configure TinyMCE.</li>\n" +
      "<li>Have a specific question? Visit the <a href=\"https://community.tinymce.com/forum/\" target=\"_blank\" rel=\"noopener\">Community Forum</a>.</li>\n" +
      "<li>We also offer enterprise grade support as part of <a href=\"https://www.tiny.cloud/pricing/\">TinyMCE Enterprise</a>.</li>\n" +
      "</ul>\n" +
      "<h2>A simple table to play with</h2>\n" +
      "<table style=\"text-align: center;\">\n" +
      "<thead>\n" +
      "<tr>\n" +
      "<th>Product</th>\n" +
      "<th>Cost</th>\n" +
      "<th>Really?</th>\n" +
      "</tr>\n" +
      "</thead>\n" +
      "<tbody>\n" +
      "<tr>\n" +
      "<td>TinyMCE</td>\n" +
      "<td>Free</td>\n" +
      "<td>YES!</td>\n" +
      "</tr>\n" +
      "<tr>\n" +
      "<td>Plupload</td>\n" +
      "<td>Free</td>\n" +
      "<td>YES!</td>\n" +
      "</tr>\n" +
      "</tbody>\n" +
      "</table>\n" +
      "<h2>Found a bug?</h2>\n" +
      "<p>If you think you have found a bug please create an issue on the <a href=\"https://github.com/tinymce/tinymce/issues\">GitHub repo</a> to report it to the developers.</p>\n" +
      "<h2>Finally ...</h2>\n" +
      "<p>Don't forget to check out our other product <a href=\"http://www.plupload.com\" target=\"_blank\" rel=\"noopener\">Plupload</a>, your ultimate upload solution featuring HTML5 upload support.</p>\n" +
      "<p>Thanks for supporting TinyMCE! We hope it helps you and your users create great content.<br />All the best from the TinyMCE team.</p>";

  public final static String localImage = "data:image/png;base64,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";

  private Utils() {
  }
}

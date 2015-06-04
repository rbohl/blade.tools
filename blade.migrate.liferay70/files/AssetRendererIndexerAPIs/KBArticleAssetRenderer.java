public class KBArticleAssetRenderer extends BaseAssetRenderer {

	@Override
	public String getSummary(Locale locale) {
		String summary = _kbArticle.getDescription();

		if (Validator.isNull(summary)) {
			summary = StringUtil.shorten(
				HtmlUtil.extractText(_kbArticle.getContent()), 200);
		}

		return summary;
	}

}
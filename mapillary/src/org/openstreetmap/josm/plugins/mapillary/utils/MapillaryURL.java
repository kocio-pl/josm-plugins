// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.plugins.mapillary.utils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.data.Bounds;

public final class MapillaryURL {
  private static final String CLIENT_ID = "T1Fzd20xZjdtR0s1VDk5OFNIOXpYdzoxNDYyOGRkYzUyYTFiMzgz";
  /** Base URL of the Mapillary API. */
  private static final String BASE_API_URL = "https://a.mapillary.com/v2/";
  private static final String BASE_WEBSITE_URL = "https://www.mapillary.com/";

  private MapillaryURL() {
    // Private constructor to avoid instantiation
  }

  /**
   * Gives you the URL for the online editor of a specific mapillary image.
   * @param key the key of the image to which you want to link
   * @return the URL of the online editor for the image with the given image key
   */
  public static URL browseEditURL(String key) {
    if (key == null || !key.matches("[a-zA-Z0-9\\-_]{22}")) {
      throw new IllegalArgumentException("Invalid image key");
    }
    return string2URL(BASE_WEBSITE_URL + "map/e/" + key);
  }

  /**
   * Gives you the URL for the online viewer of a specific mapillary image.
   * @param key the key of the image to which you want to link
   * @return the URL of the online viewer for the image with the given image key
   */
  public static URL browseImageURL(String key) {
    if (key == null || !key.matches("[a-zA-Z0-9\\-_]{22}")) {
      throw new IllegalArgumentException("Invalid image key");
    }
    return string2URL(BASE_WEBSITE_URL + "map/im/" + key);
  }

  /**
   * @return the URL where the user can view all uploaded images that are not yet published
   */
  public static URL browseUploadImageURL() {
    return string2URL(BASE_WEBSITE_URL + "map/upload/im/");
  }

  /**
   * Gives you the URL which the user should visit to initiate the OAuth authentication process
   * @param redirectURI the URI to which the user will be redirected when the authentication is finished
   * @return the URL that the user should visit to start the OAuth authentication
   */
  public static URL connectURL(String redirectURI) {
    HashMap<String, String> parts = new HashMap<>();
    if (redirectURI != null && redirectURI.length() >= 1) {
      parts.put("redirect_uri", redirectURI);
    }
    parts.put("response_type", "token");
    parts.put("scope", "user:read public:upload public:write");
    return string2URL(BASE_WEBSITE_URL + "connect"+queryString(parts));
  }

  /**
   * Gives you the API-URL where you get 20 images within the given bounds.
   * For more than 20 images you have to use different URLs with different page numbers.
   * @param bounds the bounds in which you want to search for images
   * @param page number of the page to retrieve from the API
   * @return the API-URL which gives you the images in the given bounds as JSON
   */
  public static URL searchImageURL(Bounds bounds, int page) {
    HashMap<String, String> parts = new HashMap<>();
    putBoundsInQueryStringParts(parts, bounds);
    parts.put("page", Integer.toString(page));
    parts.put("limit", "20");
    return string2URL(BASE_API_URL + "search/im/" + queryString(parts));
  }

  /**
   * Gives you the API-URL where you get 10 sequences within the given bounds.
   * For more than 10 sequences you have to use different URLs with different page numbers.
   * @param bounds the bounds in which you want to search for sequences
   * @param page number of the page to retrieve from the API
   * @return the API-URL which gives you the sequences in the given bounds as JSON
   */
  public static URL searchSequenceURL(Bounds bounds, int page) {
    HashMap<String, String> parts = new HashMap<>();
    putBoundsInQueryStringParts(parts, bounds);
    parts.put("page", Integer.toString(page));
    parts.put("limit", "10");
    return string2URL(BASE_API_URL + "search/s/" + queryString(parts));
  }

  /**
   * Gives you the API-URL where you get the traffic signs for 20 images within the given bounds.
   * For the signs from more than 20 images you have to use different URLs with different page numbers.
   * @param bounds the bounds in which you want to search for traffic signs
   * @param page number of the page to retrieve from the API
   * @return the API-URL which gives you the traffic signs in the given bounds as JSON
   */
  public static URL searchTrafficSignURL(Bounds bounds, int page) {
    HashMap<String, String> parts = new HashMap<>();
    putBoundsInQueryStringParts(parts, bounds);
    parts.put("page", Integer.toString(page));
    parts.put("limit", "20");
    return string2URL(BASE_API_URL + "search/im/or/" + queryString(parts));
  }

  /**
   * @return the URL where you'll find the upload secrets as JSON
   */
  public static URL uploadSecretsURL() {
    return string2URL(BASE_API_URL + "me/uploads/secrets/" + queryString(null));
  }

  /**
   * @return the URL where you'll find information about the user account as JSON
   */
  public static URL userURL() {
    return string2URL(BASE_API_URL + "me/" + queryString(null));
  }

  /**
   * Adds the given {@link Bounds} to a {@link Map} that contains the parts of a query string.
   * @param parts the parts of a query string
   * @param bounds the bounds that will be added to the query string
   */
  private static void putBoundsInQueryStringParts(Map<String, String> parts, Bounds bounds) {
    if (bounds != null) {
      parts.put("min_lat", String.format(Locale.UK, "%f", bounds.getMin().lat()));
      parts.put("max_lat", String.format(Locale.UK, "%f", bounds.getMax().lat()));
      parts.put("min_lon", String.format(Locale.UK, "%f", bounds.getMin().lon()));
      parts.put("max_lon", String.format(Locale.UK, "%f", bounds.getMax().lon()));
    }
  }

  /**
   * Builds a query string from it's parts that are supplied as a {@link Map}
   * @param parts the parts of the query string
   * @return the constructed query string (including a leading ?)
   */
  private static String queryString(Map<String, String> parts) {
    StringBuilder ret = new StringBuilder("?client_id=").append(CLIENT_ID);
    if (parts != null) {
      for (Entry<String, String> entry : parts.entrySet()) {
        try {
          ret.append('&')
            .append(URLEncoder.encode(entry.getKey(), "UTF-8"))
            .append('=')
            .append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
          Main.error(e); // This should not happen, as the encoding is hard-coded
        }
      }
    }
    return ret.toString();
  }

  /**
   * Converts a {@link String} into a {@link URL} without throwing a {@link MalformedURLException}.
   * Instead such an exception will lead to an {@link Main#error(Throwable)}.
   * So you should be very confident that your URL is well-formed when calling this method.
   * @param string the String describing the URL
   * @return the URL that is constructed from the given string
   */
  private static URL string2URL(String string) {
    try {
      return new URL(string);
    } catch (MalformedURLException e) {
      Main.error(new Exception("The "+MapillaryURL.class.getSimpleName()+" class produces malformed URLs!", e));
      return null;
    }
  }
}

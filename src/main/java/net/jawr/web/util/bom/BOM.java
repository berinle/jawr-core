package net.jawr.web.util.bom;

public class BOM {

	/**
	 * NONE.
	 */
	public static final BOM NONE = new BOM(new byte[] {}, "NONE");

	/**
	 * UTF-8 BOM (EF BB BF).
	 */
	public static final BOM UTF_8 = new BOM(new byte[] { (byte) 0xEF,
			(byte) 0xBB, (byte) 0xBF }, "UTF-8");

	/**
	 * UTF-16, little-endian (FF FE).
	 */
	public static final BOM UTF_16_LE = new BOM(new byte[] { (byte) 0xFF,
			(byte) 0xFE }, "UTF-16LE");

	/**
	 * UTF-16, big-endian (FE FF).
	 */
	public static final BOM UTF_16_BE = new BOM(new byte[] { (byte) 0xFE,
			(byte) 0xFF }, "UTF-16BE");

	/**
	 * UTF-32, little-endian (FF FE 00 00).
	 */
	public static final BOM UTF_32_LE = new BOM(new byte[] { (byte) 0xFF,
			(byte) 0xFE, (byte) 0x00, (byte) 0x00 }, "UTF-32LE");

	/**
	 * UTF-32, big-endian (00 00 FE FF).
	 */
	public static final BOM UTF_32_BE = new BOM(new byte[] { (byte) 0x00,
			(byte) 0x00, (byte) 0xFE, (byte) 0xFF }, "UTF-32BE");

	final byte bytes[];
	private final String charset;
	
	private BOM(final byte bom[], final String charset) {
		assert (bom != null) : "invalid BOM: null is not allowed";
		assert (charset != null) : "invalid description: null is not allowed";
		assert (charset.length() != 0) : "invalid description: empty string is not allowed";

		this.bytes = bom;
		this.charset = charset;
	}
	
	/**
	 * Returns the charset.
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * Returns the bytes corresponding to this <code>BOM</code> value.
	 */
	public final byte[] getBytes() {
		final int length = bytes.length;
		final byte[] result = new byte[length];

		// Make a defensive copy
		System.arraycopy(bytes, 0, result, 0, length);

		return result;
	}
	
	/**
	 * Returns a <code>String</code> representation of this <code>BOM</code>
	 * value.
	 */
	public final String toString() {
		return charset;
	}

}
